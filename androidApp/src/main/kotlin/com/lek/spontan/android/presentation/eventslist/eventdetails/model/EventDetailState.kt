package com.lek.spontan.android.presentation.eventslist.eventdetails.model

import com.lek.spontan.events.domain.models.DateData
import com.lek.spontan.events.domain.models.TimeData

data class EventDetailState(
    val title: String,
    val description: String,
    val dateData: DateData,
    val timeData: TimeData,
    val isLoading: Boolean,
    val isEventAdded: Boolean,
    val isDateVisible: Boolean,
    val isTimeVisible: Boolean,
    val error: String
) {
    val isValid: Boolean
        get() = title.isNotEmpty() && !dateData.isNull() && !timeData.isNull

    fun fromState(state: EventDetailState): EventDetailState = state

    companion object {
        val EMPTY = EventDetailState(
            title = "",
            description = "",
            dateData = DateData.EMPTY,
            timeData = TimeData.EMPTY,
            isLoading = false,
            isEventAdded = false,
            isDateVisible = false,
            isTimeVisible = false,
            error = ""
        )
    }
}

sealed interface EventDetailEvent {
    object OnAddEventClicked : EventDetailEvent
    data class OnTitleChanged(val title: String) : EventDetailEvent
    data class OnDescriptionChanged(val description: String) : EventDetailEvent
    data class OnTimeDataChanged(val timeData: TimeData) : EventDetailEvent
    data class OnDateDataChanged(val dateData: DateData) : EventDetailEvent
    data class ShowDatePicker(val shouldShow: Boolean) : EventDetailEvent
    data class ShowTimePicker(val shouldShow: Boolean) : EventDetailEvent
    data class InitialiseData(val data: EventDetailState) : EventDetailEvent
}