package com.lek.spontan.di

import com.lek.spontan.authentication.data.AuthRepository
import com.lek.spontan.authentication.domain.IAuthRepository
import com.lek.spontan.authentication.domain.usecases.LoginUseCase
import com.lek.spontan.authentication.domain.usecases.SignUpUseCase
import com.lek.spontan.data.NetworkInterface
import com.lek.spontan.httpClient

object DI {
    private val networkInterface = NetworkInterface(httpClient)
    private val repository: IAuthRepository = AuthRepository(networkInterface)
    val loginUseCase: LoginUseCase = LoginUseCase(repository)
    val signupUseCase: SignUpUseCase = SignUpUseCase(repository)
}