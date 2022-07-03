package com.lek.spontan.authentication.domain.usecase

import com.lek.spontan.authentication.domain.repository.IAuthRepository
import com.lek.spontan.authentication.domain.usecases.SignUpUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlin.test.Test
import kotlinx.coroutines.runBlocking

class SignUpUseCaseTest {

    private val repository: IAuthRepository = mockk(relaxed = true)
    private val signUpUseCase = SignUpUseCase(repository)

    @Test
    fun testSignUp(): Unit = runBlocking {
        coEvery { repository.signup(any()) }.coAnswers { mockk() }
        signUpUseCase.invoke(mockk())
        coVerify { repository.signup(any()) }
    }
}