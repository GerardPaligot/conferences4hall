package org.gdglille.devfest.backend.third.parties.openplanner

import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import org.gdglille.devfest.backend.NotAcceptableException
import org.gdglille.devfest.backend.categories.CategoryDao
import org.gdglille.devfest.backend.categories.CategoryDb
import org.gdglille.devfest.backend.events.EventDao
import org.gdglille.devfest.backend.events.EventDb
import org.gdglille.devfest.backend.formats.FormatDao
import org.gdglille.devfest.backend.formats.FormatDb
import org.gdglille.devfest.backend.internals.CommonApi
import org.gdglille.devfest.backend.schedulers.ScheduleDb
import org.gdglille.devfest.backend.schedulers.ScheduleItemDao
import org.gdglille.devfest.backend.sessions.SessionDao
import org.gdglille.devfest.backend.sessions.SessionDb
import org.gdglille.devfest.backend.speakers.SpeakerDao
import org.gdglille.devfest.backend.speakers.SpeakerDb

@Suppress("LongParameterList")
class OpenPlannerRepository(
    private val openPlannerApi: OpenPlannerApi,
    private val commonApi: CommonApi,
    private val eventDao: EventDao,
    private val speakerDao: SpeakerDao,
    private val sessionDao: SessionDao,
    private val categoryDao: CategoryDao,
    private val formatDao: FormatDao,
    private val scheduleItemDao: ScheduleItemDao
) {
    suspend fun update(eventId: String, apiKey: String) = coroutineScope {
        val event = eventDao.getVerified(eventId, apiKey)
        val config = event.openPlannerConfig
            ?: throw NotAcceptableException("OpenPlanner config not initialized")
        val openPlanner = openPlannerApi.fetchPrivateJson(config.eventId, config.privateId)
        val categories = openPlanner.event.categories
            .map { async { createOrMergeCategory(eventId, it) } }
            .awaitAll()
        val formats = openPlanner.event.formats
            .map { async { createOrMergeFormat(eventId, it) } }
            .awaitAll()
        val allSpeakers = openPlanner.sessions
            .map { it.speakerIds }.flatten()
        val speakers = openPlanner.speakers
            .filter { allSpeakers.contains(it.id) }
            .map { async { createOrMergeSpeaker(eventId, it) } }
            .awaitAll()
        val trackIds = openPlanner.event.tracks.map { it.id }
        openPlanner.sessions
            .map { async { createOrMergeTalks(event, openPlanner.event.tracks, it) } }
            .awaitAll()
        val schedules = openPlanner.sessions
            .filter { it.trackId != null && it.dateStart != null && it.dateEnd != null }
            .groupBy { it.dateStart }
            .map {
                async {
                    it.value
                        .sortedWith { sessionA, sessionB ->
                            trackIds.indexOf(sessionA.trackId)
                                .compareTo(trackIds.indexOf(sessionB.trackId))
                        }
                        .mapIndexed { index, sessionOP ->
                            createOrMergeSchedule(
                                eventId,
                                index,
                                sessionOP,
                                openPlanner.event.tracks
                            )
                        }
                }
            }
            .awaitAll()
            .flatten()
        clean(event, categories, formats, speakers, schedules)
        eventDao.updateAgendaUpdatedAt(event)
    }

    @Suppress("LongParameterList")
    private suspend fun clean(
        event: EventDb,
        categories: List<CategoryDb>,
        formats: List<FormatDb>,
        speakers: List<SpeakerDb>,
        schedules: List<ScheduleDb>
    ) = coroutineScope {
        categoryDao.deleteDiff(event.slugId, categories.map { it.id!! })
        formatDao.deleteDiff(event.slugId, formats.map { it.id!! })
        speakerDao.deleteDiff(event.slugId, speakers.map { it.id })
        scheduleItemDao.deleteDiff(event.slugId, schedules.map { it.id })
        val talkIds = schedules
            .filter { it.talkId != null && event.eventSessionTracks.contains(it.room).not() }
            .map { it.talkId!! }
        sessionDao.deleteDiffTalkSessions(event.slugId, talkIds)
        val eventSessionIds = schedules
            .filter { it.talkId != null && event.eventSessionTracks.contains(it.room) }
            .map { it.talkId!! }
        sessionDao.deleteDiffEventSessions(event.slugId, eventSessionIds)
    }

    private suspend fun createOrMergeCategory(eventId: String, category: CategoryOP): CategoryDb {
        val existing = categoryDao.get(eventId, category.id)
        return if (existing == null) {
            val item = category.convertToDb()
            categoryDao.createOrUpdate(eventId, item)
            item
        } else {
            val item = existing.mergeWith(category)
            categoryDao.createOrUpdate(eventId, item)
            item
        }
    }

    private suspend fun createOrMergeFormat(eventId: String, format: FormatOP): FormatDb {
        val existing = formatDao.get(eventId, format.id)
        return if (existing == null) {
            val item = format.convertToDb()
            formatDao.createOrUpdate(eventId, item)
            item
        } else {
            val item = existing.mergeWith(format)
            formatDao.createOrUpdate(eventId, item)
            item
        }
    }

    private suspend fun createOrMergeSpeaker(eventId: String, speaker: SpeakerOP): SpeakerDb {
        val existing = speakerDao.get(eventId, speaker.id)
        return if (existing == null) {
            val photoUrl = getAvatarUrl(eventId, speaker)
            val item = speaker.convertToDb(photoUrl)
            speakerDao.createOrUpdate(eventId, item)
            item
        } else {
            val photoUrl = getAvatarUrl(eventId, speaker)
            val item = existing.mergeWith(photoUrl, speaker)
            speakerDao.createOrUpdate(eventId, item)
            item
        }
    }

    private suspend fun getAvatarUrl(eventId: String, speaker: SpeakerOP) = try {
        if (speaker.photoUrl != null) {
            val avatar = commonApi.fetchByteArray(speaker.photoUrl)
            val bucketItem = speakerDao.saveProfile(eventId, speaker.id, avatar)
            bucketItem.url
        } else {
            null
        }
    } catch (_: Throwable) {
        speaker.photoUrl
    }

    private suspend fun createOrMergeTalks(
        event: EventDb,
        tracks: List<TrackOP>,
        session: SessionOP
    ): SessionDb {
        val track = tracks.find { it.id == session.trackId }
        return if (event.eventSessionTracks.contains(track?.name)) {
            val existing = sessionDao.getEventSession(event.slugId, session.id)
            val item = existing?.mergeWith(session) ?: session.convertToEventSessionDb()
            sessionDao.createOrUpdate(event.slugId, item)
            item
        } else {
            val existing = sessionDao.getTalkSession(event.slugId, session.id)
            val item = existing?.mergeWith(session) ?: session.convertToTalkDb()
            sessionDao.createOrUpdate(event.slugId, item)
            item
        }
    }

    private suspend fun createOrMergeSchedule(
        eventId: String,
        order: Int,
        session: SessionOP,
        tracks: List<TrackOP>
    ): ScheduleDb {
        val item = session.convertToScheduleDb(order, tracks)
        scheduleItemDao.createOrUpdate(eventId, item)
        return item
    }
}
