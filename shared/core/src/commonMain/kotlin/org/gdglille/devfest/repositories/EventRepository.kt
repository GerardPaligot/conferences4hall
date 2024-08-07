package org.gdglille.devfest.repositories

import com.rickclephas.kmp.nativecoroutines.NativeCoroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import org.gdglille.devfest.database.EventDao
import org.gdglille.devfest.exceptions.EventSavedException
import org.gdglille.devfest.models.ui.EventInfoUi
import org.gdglille.devfest.models.ui.EventItemListUi
import org.gdglille.devfest.network.ConferenceApi

interface EventRepository {
    suspend fun fetchAndStoreEventList()
    fun events(): Flow<EventItemListUi>
    fun currentEvent(): Flow<EventInfoUi?>
    fun isInitialized(defaultEvent: String? = null): Boolean
    fun saveEventId(eventId: String)
    fun deleteEventId()

    object Factory {
        fun create(api: ConferenceApi, eventDao: EventDao): EventRepository =
            EventRepositoryImpl(api = api, eventDao = eventDao)
    }
}

class EventRepositoryImpl(
    val api: ConferenceApi,
    val eventDao: EventDao
) : EventRepository {
    @NativeCoroutineScope
    private val coroutineScope: CoroutineScope = MainScope()

    override suspend fun fetchAndStoreEventList() = coroutineScope {
        val events = api.fetchEventList()
        eventDao.insertEventItems(future = events.future, past = events.past)
    }

    override fun events(): Flow<EventItemListUi> = eventDao.fetchEventList()
    override fun currentEvent(): Flow<EventInfoUi?> = eventDao.fetchCurrentEvent()

    override fun isInitialized(defaultEvent: String?): Boolean = try {
        eventDao.getEventId()
        true
    } catch (_: EventSavedException) {
        if (defaultEvent != null) {
            eventDao.insertEventId(defaultEvent)
            true
        } else {
            false
        }
    }

    override fun saveEventId(eventId: String) {
        eventDao.insertEventId(eventId)
    }

    override fun deleteEventId() {
        eventDao.deleteEventId()
    }
}
