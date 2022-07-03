package com.lek.spontan.events.domain

import com.lek.spontan.events.domain.models.DateData
import com.lek.spontan.events.domain.models.NEGATIVE_TIME
import com.lek.spontan.events.domain.models.TimeData
import kotlinx.coroutines.flow.StateFlow
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant

interface IEventsRepository {
    fun setSelectedEvent(event: EventDomainData)
    fun getSelectedEvent(): EventDomainData?
    val events: StateFlow<EventDomainResult>
    suspend fun getEvents(accessToken: String): EventDomainResult
    suspend fun addEvent(event: EventDomainData, accessToken: String): EventDomainResult
    suspend fun deleteEvent(id: String, accessToken: String)
    suspend fun deleteAllEvents(accessToken: String)
}

data class EventDomainResult(
    val data: List<EventDomainData>,
    val error: String
) {
    companion object {
        val EMPTY = EventDomainResult(listOf(), "")
    }
}

data class EventDomainData(
    val id: String,
    val title: String,
    val description: String?,
    val photo: String?,
    val dateData: DateData?,
    val timeData: TimeData?
) {
    val startTimeInMilliSeconds: Long?
        get() = if (dateData != null && timeData != null && !dateData.isNull() && !timeData.isNull) {
            val localTime = LocalDateTime(
                year = dateData.year,
                monthNumber = dateData.month,
                dayOfMonth = dateData.day,
                hour = timeData.hour,
                minute = if (timeData.minute == NEGATIVE_TIME) 0 else timeData.minute
            )
            localTime.toInstant(TimeZone.UTC).toEpochMilliseconds()
        } else {
            null
        }
}