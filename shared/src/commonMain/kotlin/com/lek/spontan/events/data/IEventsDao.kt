package com.lek.spontan.events.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

interface IEventsDao {
    fun getEvents(): List<Event>
    suspend fun saveEvent(event: Event)
    suspend fun deleteEvent(id: String)
    suspend fun saveEvents(events: List<Event>)
    suspend fun deleteAllEvents()
}

@Serializable
data class Event(
    @SerialName("id")
    val id: String,
    @SerialName("title")
    val title: String,
    @SerialName("description")
    val description: String?,
    @SerialName("cover_image")
    val photo: String? = "_",
    @SerialName("time")
    val startTime: Long?
)