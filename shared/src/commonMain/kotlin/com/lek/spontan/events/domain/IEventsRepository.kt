package com.lek.spontan.events.domain

interface IEventsRepository {
    fun getEvents(): List<EventDomainData>
    suspend fun addEvent(event: EventDomainData)
    suspend fun deleteEvent(id: String)
    suspend fun deleteAllEvents()
}

data class EventDomainData(
    val id: String,
    val title: String,
    val description: String?,
    val color: Color?,
    val photo: String?,
    val startTime: Long?
)

enum class Color {
    GREY, ORANGE, GREEN
}