package com.lek.spontan.android.presentation.eventslist.eventdetails

import androidx.lifecycle.viewModelScope
import com.lek.spontan.android.presentation.eventslist.eventdetails.model.EventDetailEvent
import com.lek.spontan.android.presentation.eventslist.eventdetails.model.EventDetailState
import com.lek.spontan.android.presentation.shared.BaseViewModel
import com.lek.spontan.di.DI
import com.lek.spontan.events.domain.CreateEventUseCase
import com.lek.spontan.events.domain.EventDomainData
import com.lek.spontan.events.domain.models.DateData
import com.lek.spontan.events.domain.models.TimeData
import java.util.UUID
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EventDetailViewModel(
    private val createEventUseCase: CreateEventUseCase = DI.createEventUseCase
) : BaseViewModel<EventDetailState, EventDetailEvent>() {

    override fun createInitialState(): EventDetailState = EventDetailState.EMPTY

    private fun addEvent() {
        viewModelScope.launch(Dispatchers.IO) {
            setState { copy(error = "", isLoading = true) }
            val result = createEventUseCase(uiState.value.toDomainData())
            if (result.error.isNotEmpty()) {
                setState { copy(error = result.error, isLoading = false) }
            } else {
                setState { copy(isEventAdded = true, error = "") }
            }
        }
    }

    override fun onEvent(event: EventDetailEvent) {
        when (event) {
            EventDetailEvent.OnAddEventClicked -> addEvent()
            is EventDetailEvent.OnDateDataChanged ->
                setState {
                    copy(
                        dateData = if (event.dateData != DateData.EMPTY) event.dateData else dateData,
                        isDateVisible = false,
                        error = ""
                    )
                }
            is EventDetailEvent.OnDescriptionChanged ->
                setState { copy(description = event.description, error = "") }
            is EventDetailEvent.OnTimeDataChanged ->
                setState {
                    copy(
                        timeData = if (event.timeData != TimeData.EMPTY) event.timeData else timeData,
                        isTimeVisible = false,
                        error = ""
                    )
                }
            is EventDetailEvent.OnTitleChanged ->
                setState { copy(title = event.title, error = "") }
            is EventDetailEvent.ShowDatePicker ->
                setState { copy(isDateVisible = event.shouldShow) }
            is EventDetailEvent.ShowTimePicker ->
                setState { copy(isTimeVisible = event.shouldShow) }
            is EventDetailEvent.InitialiseData -> setState { this.fromState(event.data) }
        }
    }

    private fun EventDetailState.toDomainData(): EventDomainData =
        EventDomainData(
            id = UUID.randomUUID().toString(),
            title = title,
            description = description,
            photo = null,
            dateData = dateData,
            timeData = timeData
        )

    fun resolveBackClicked() {
        setState { copy(isEventAdded = false) }
    }
}