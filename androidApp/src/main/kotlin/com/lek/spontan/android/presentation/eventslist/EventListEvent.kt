package com.lek.spontan.android.presentation.eventslist

import com.lek.spontan.events.domain.EventDomainData

sealed interface EventListEvent {
    object AddEventClicked : EventListEvent
    data class EventClicked(val event: EventDomainData) : EventListEvent
}