package com.lek.spontan.authentication.domain.usecases

import com.lek.spontan.authentication.domain.repository.IUserRepository

class GetUserUseCase(
    private val repo: IUserRepository
) {
    suspend operator fun invoke() = repo.getUser()
}