package com.lek.spontan.events.data

import com.lek.spontan.events.domain.Color

interface IEventsDao {
    fun getEvents(): List<Event>
    suspend fun saveEvent(event: Event)
    suspend fun deleteEvent(id: String)
    suspend fun saveEvents(events: List<Event>)
    suspend fun deleteAllEvents()
}

data class Event(
    val id: String,
    val title: String,
    val description: String?,
    val color: Color?,
    val photo: String?,
    val startTime: Long?
)

fun Color.toLong(): Long {
    return ordinal.toLong()
}

fun Int.toColor(): Color? {
    return Color.values().firstOrNull { it.ordinal == this }
}