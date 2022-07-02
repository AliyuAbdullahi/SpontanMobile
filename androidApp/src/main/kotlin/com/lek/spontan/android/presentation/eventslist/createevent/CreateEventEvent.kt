package com.lek.spontan.android.presentation.eventslist.createevent

sealed interface CreateEventEvent {
    data class OnTitleTyped(val title: String) : CreateEventEvent
    data class OnDescriptionTyped(val description: String) : CreateEventEvent
    data class OnDateTyped(val date: String) : CreateEventEvent
    data class OnTimeTyped(val time: String) : CreateEventEvent
    object OnCreateEventClick : CreateEventEvent
}