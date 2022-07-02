package com.lek.spontan.events.domain

class GetEventsUseCase(
    private val repository: IEventsRepository
) {
    suspend operator fun invoke() = repository.events
}