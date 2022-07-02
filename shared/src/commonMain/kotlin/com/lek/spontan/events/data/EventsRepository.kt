package com.lek.spontan.events.data

import com.lek.spontan.data.NetworkInterface
import com.lek.spontan.data.Routes
import com.lek.spontan.events.domain.DeleteRequest
import com.lek.spontan.events.domain.EventDomainData
import com.lek.spontan.events.domain.EventDomainResult
import com.lek.spontan.events.domain.IEventsRepository
import com.lek.spontan.events.domain.models.DateData
import com.lek.spontan.events.domain.models.TimeData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

class EventsRepository(
    private val eventDao: IEventsDao,
    private val networkInterface: NetworkInterface
) : IEventsRepository {

    private var selectedEvent: EventDomainData? = null

    override fun setSelectedEvent(event: EventDomainData) {
        selectedEvent = event
    }

    override fun getSelectedEvent(): EventDomainData? = selectedEvent

    private val mutableEventResult: MutableStateFlow<EventDomainResult> =
        MutableStateFlow(EventDomainResult.EMPTY)

    override val events: StateFlow<EventDomainResult>
        get() = mutableEventResult

    override suspend fun getEvents(accessToken: String): Unit =
        try {
            if (accessToken.isEmpty()) {
                mutableEventResult.value = getEventFromLocalDatabase()
            } else {
                mutableEventResult.value = getEventsFromServer(accessToken)
            }
        } catch (error: Throwable) {
            mutableEventResult.value = EventDomainResult(
                data = eventDao.getEvents().map { it.toDomainModel() },
                error = error.message.toString()
            )
        }

    override suspend fun addEvent(
        event: EventDomainData,
        accessToken: String
    ): EventDomainResult = try {
        if (accessToken.isEmpty()) {
            eventDao.saveEvent(event.toDataModel())
            val newList = eventDao.getEvents()
            mutableEventResult.value = EventDomainResult(
                data = newList.map { it.toDomainModel() }.reversed(),
                error = ""
            )
            mutableEventResult.value
        } else {
            val result = networkInterface.post<Event, EventResponse>(
                Routes.EVENT,
                event.toDataModel(),
                accessToken
            )
            eventDao.saveEvents(result.events)
            val newList = eventDao.getEvents()
            mutableEventResult.value = EventDomainResult(
                data = newList.map { it.toDomainModel() }.reversed(),
                error = ""
            )
            mutableEventResult.value
        }
    } catch (error: Throwable) {
        mutableEventResult.value = EventDomainResult(
            data = eventDao.getEvents().map { it.toDomainModel() },
            error = error.message.toString()
        )
        mutableEventResult.value
    }

    override suspend fun deleteEvent(id: String, accessToken: String) {
        try {
            if (accessToken.isEmpty()) {
                eventDao.deleteEvent(id)
            } else {
                val result: EventsResponse = networkInterface.delete(
                    path = Routes.EVENT,
                    requestBody = DeleteRequest(id),
                    accessToken
                )
                mutableEventResult.value = EventDomainResult(
                    data = result.events.map { it.toDomainModel() },
                    error = ""
                )
            }
        } catch (error: Throwable) {

        }
    }

    override suspend fun deleteAllEvents(accessToken: String) {
        if (accessToken.isEmpty()) {
            eventDao.deleteAllEvents()
        } else {
            // delete all events from server
        }
    }

    private fun getEventFromLocalDatabase() = EventDomainResult(
        data = eventDao.getEvents().map { it.toDomainModel() }
            .sortedBy { it.startTimeInMilliSeconds }.reversed(),
        error = ""
    )

    private suspend fun getEventsFromServer(accessToken: String): EventDomainResult {
        val result = networkInterface.get<EventsResponse>(Routes.EVENT, accessToken)
        return if (result.events.isNotEmpty()) {
            eventDao.saveEvents(result.events)
            EventDomainResult(
                data = result.events.map { it.toDomainModel() }.reversed(),
                error = ""
            )
        } else {
            EventDomainResult(
                data = eventDao.getEvents().map { it.toDomainModel() }.reversed(),
                error = "Failed to fetch data from network"
            )
        }
    }
}

fun EventDomainData.toDataModel(): Event =
    Event(
        id = id,
        title = title,
        description = description,
        photo = photo,
        startTime = startTimeInMilliSeconds
    )

fun Event.toDomainModel(): EventDomainData =
    EventDomainData(
        id = id,
        title = title,
        description = description,
        photo = photo,
        timeData = startTime?.toTimeData(),
        dateData = startTime?.toDateData(),
    )

@Serializable
data class EventResponse(
    @SerialName("events")
    val events: List<Event>
)

fun Long.toTimeData(): TimeData {
    val instant = Instant.fromEpochMilliseconds(this)
    val datetimeInUtc: LocalDateTime = instant.toLocalDateTime(TimeZone.UTC)
    return TimeData(datetimeInUtc.minute, datetimeInUtc.hour)
}

fun Long.toDateData(): DateData {
    val instant = Instant.fromEpochMilliseconds(this)
    val datetimeInUtc: LocalDateTime = instant.toLocalDateTime(TimeZone.UTC)
    return DateData(datetimeInUtc.dayOfMonth, datetimeInUtc.monthNumber, datetimeInUtc.year)
}