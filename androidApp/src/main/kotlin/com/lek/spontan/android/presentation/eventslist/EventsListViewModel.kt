package com.lek.spontan.android.presentation.eventslist

import androidx.lifecycle.viewModelScope
import com.lek.spontan.android.presentation.shared.BaseViewModel
import com.lek.spontan.di.DI
import com.lek.spontan.events.domain.GetEventsUseCase
import com.lek.spontan.events.domain.LoadEventsUseCase
import kotlinx.coroutines.launch

class EventsListViewModel(
    private val getEventsUseCase: GetEventsUseCase = DI.getEventsUseCase,
    private val loadEventsUseCase: LoadEventsUseCase = DI.loadEventsUseCase
) : BaseViewModel<EventListState, EventListEvent>() {

    override fun createInitialState(): EventListState = EventListState.EMPTY

    override fun onEvent(event: EventListEvent) {
        when (event) {
            EventListEvent.AddEventClicked -> setState { this.copy(addEventClicked = true, error = "") }
            is EventListEvent.EventClicked -> {}
        }
    }

    init {
        viewModelScope.launch {
            getEventsUseCase().collect { domainResult ->
                setState {
                    this.copy(events = domainResult.data, isEmptyState = domainResult.data.isEmpty())
                }
            }
        }
        viewModelScope.launch {
            setState { this.copy(isLoading = true) }
            loadEventsUseCase()
            setState { this.copy(isLoading = false) }
        }
    }
}