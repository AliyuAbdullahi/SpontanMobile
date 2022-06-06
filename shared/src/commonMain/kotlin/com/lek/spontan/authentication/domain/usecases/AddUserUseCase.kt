package com.lek.spontan.authentication.domain.usecases

import com.lek.spontan.authentication.domain.repository.IUserRepository
import com.lek.spontan.authentication.domain.repository.UserDomainModel

class AddUserUseCase(
    private val repository: IUserRepository
) {
    suspend operator fun invoke(user: UserDomainModel) = repository.saveUser(user)
}