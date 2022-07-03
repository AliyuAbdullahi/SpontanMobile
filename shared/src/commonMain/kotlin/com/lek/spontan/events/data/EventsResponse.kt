package com.lek.spontan.events.data

import com.lek.spontan.events.data.Event
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EventsResponse(
    @SerialName("events")
    val events: List<Event>
)