package com.lek.spontan.events.domain

class GetSelectedEventUseCase(
    private val repository: IEventsRepository
) {
    operator fun invoke() = repository.getSelectedEvent()
}