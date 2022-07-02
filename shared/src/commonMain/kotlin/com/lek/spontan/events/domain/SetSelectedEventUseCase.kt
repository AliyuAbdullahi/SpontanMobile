package com.lek.spontan.events.domain

class SetSelectedEventUseCase(
    private val repository: IEventsRepository
) {
    operator fun invoke(eventDomainData: EventDomainData) =
        repository.setSelectedEvent(eventDomainData)
}