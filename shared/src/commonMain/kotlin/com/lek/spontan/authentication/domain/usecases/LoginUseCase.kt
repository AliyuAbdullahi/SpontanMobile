package com.lek.spontan.authentication.domain.usecases

import com.lek.spontan.authentication.domain.repository.IAuthRepository
import com.lek.spontan.authentication.domain.models.LoginRequestModel

class LoginUseCase(private val authRepository: IAuthRepository) {
    suspend operator fun invoke(requestModel: LoginRequestModel) = authRepository.login(requestModel)
}