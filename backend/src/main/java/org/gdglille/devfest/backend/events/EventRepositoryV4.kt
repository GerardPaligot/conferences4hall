package org.gdglille.devfest.backend.events

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import org.gdglille.devfest.backend.categories.CategoryDao
import org.gdglille.devfest.backend.categories.convertToModel
import org.gdglille.devfest.backend.formats.FormatDao
import org.gdglille.devfest.backend.formats.convertToModel
import org.gdglille.devfest.backend.schedules.ScheduleItemDao
import org.gdglille.devfest.backend.schedules.convertToEventSession
import org.gdglille.devfest.backend.schedules.convertToModelV4
import org.gdglille.devfest.backend.sessions.SessionDao
import org.gdglille.devfest.backend.sessions.convertToModel
import org.gdglille.devfest.backend.speakers.SpeakerDao
import org.gdglille.devfest.backend.speakers.convertToModel
import org.gdglille.devfest.models.AgendaV4

class EventRepositoryV4(
    private val speakerDao: SpeakerDao,
    private val sessionDao: SessionDao,
    private val categoryDao: CategoryDao,
    private val formatDao: FormatDao,
    private val scheduleItemDao: ScheduleItemDao,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend fun agenda(eventDb: EventDb) = coroutineScope {
        val schedules = async(context = dispatcher) {
            scheduleItemDao.getAll(eventDb.slugId).map { it.convertToModelV4() }
        }.await()
        // For older event, get their break sessions
        val breaks = schedules
            .filter { it.id.contains("-pause") }
            .map { it.convertToEventSession() }
        val sessions = async(context = dispatcher) {
            sessionDao.getAll(eventDb.slugId).map { it.convertToModel(eventDb) }
        }
        val formats = async(context = dispatcher) {
            formatDao.getAll(eventDb.slugId).map { it.convertToModel() }
        }
        val categories = async(context = dispatcher) {
            categoryDao.getAll(eventDb.slugId).map { it.convertToModel() }
        }
        val speakers = async(context = dispatcher) {
            speakerDao.getAll(eventDb.slugId).map { it.convertToModel() }
        }
        return@coroutineScope AgendaV4(
            schedules = schedules,
            sessions = sessions.await() + breaks,
            formats = formats.await(),
            categories = categories.await(),
            speakers = speakers.await()
        )
    }
}
