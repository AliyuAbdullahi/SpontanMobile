package com.lek.spontan.events.domain

import com.lek.spontan.authentication.domain.repository.IUserRepository

class CreateEventUseCase(
    private val userRepository: IUserRepository,
    private val eventRepository: IEventsRepository
) {
    suspend operator fun invoke(eventDomainData: EventDomainData) =
        userRepository.getUser()?.accessToken?.let {
            eventRepository.addEvent(eventDomainData, accessToken = it)
        } ?: eventRepository.addEvent(eventDomainData, accessToken = "")
}