package com.lek.spontan.android.presentation.eventslist.createevent

import androidx.lifecycle.viewModelScope
import com.lek.spontan.android.presentation.shared.BaseViewModel
import com.lek.spontan.events.domain.CreateEventUseCase
import com.lek.spontan.events.domain.GetSelectedEventUseCase
import kotlinx.coroutines.launch

class CreateEventViewModel(
    private val createEventUseCase: CreateEventUseCase,
    getSelectedEventUseCase: GetSelectedEventUseCase
) : BaseViewModel<CreateEventState, CreateEventEvent>() {

    override fun createInitialState(): CreateEventState = CreateEventState.EMPTY

    private fun createEvent() {
        setState { this.copy(isLoading = true) }
        viewModelScope.launch {
            try {
                createEventUseCase(currentState.toDomainData())
                setState { this.copy(isEventCreated = true, error = "", isLoading = false) }
            } catch (error: Throwable) {
                setState { this.copy(error = error.toString(), isLoading = false) }
            }
        }
    }

    override fun onEvent(event: CreateEventEvent) =
        when (event) {
            CreateEventEvent.OnCreateEventClick -> createEvent()
            is CreateEventEvent.OnDateTyped -> setState { this.copy(date = event.date) }
            is CreateEventEvent.OnDescriptionTyped -> setState { this.copy(description = event.description) }
            is CreateEventEvent.OnTimeTyped -> setState { this.copy(time = event.time) }
            is CreateEventEvent.OnTitleTyped -> setState { this.copy(title = event.title) }
        }

    init {
        getSelectedEventUseCase()?.let { domainData ->
            setState {
                this.copy(
                    id = domainData.id,
                    title = domainData.title,
                    description = domainData.description ?: ""
                )
            }
        }
    }
}