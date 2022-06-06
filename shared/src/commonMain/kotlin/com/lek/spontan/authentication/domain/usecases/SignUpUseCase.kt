package com.lek.spontan.authentication.domain.usecases

import com.lek.spontan.authentication.domain.repository.IAuthRepository
import com.lek.spontan.authentication.domain.models.SignUpRequestModel

data class SignUpUseCase(
    private val repository: IAuthRepository
) {
    suspend operator fun invoke(requestModel: SignUpRequestModel) = repository.signup(requestModel)
}