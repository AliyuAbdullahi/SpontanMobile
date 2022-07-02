package com.lek.spontan.android.presentation.eventslist

import com.lek.spontan.events.domain.EventDomainData

data class EventListState(
    val events: List<EventDomainData>,
    val addEventClicked: Boolean,
    val isLoading: Boolean,
    val isEmptyState: Boolean,
    val error: String
) {
    companion object {
        val EMPTY = EventListState(
            listOf(),
            addEventClicked = false,
            isLoading = false,
            isEmptyState = false,
            error = ""
        )
    }
}