package com.lek.spontan.events.domain

import com.lek.spontan.authentication.domain.repository.IUserRepository

class LoadEventsUseCase(
    private val userRepository: IUserRepository,
    private val eventRepository: IEventsRepository
) {
    suspend operator fun invoke() = userRepository.getUser()?.accessToken?.let {
        eventRepository.getEvents(it)
    } ?: eventRepository.getEvents("")
}