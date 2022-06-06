package com.lek.spontan.authentication.domain.usecase

import com.lek.spontan.authentication.domain.repository.IAuthRepository
import com.lek.spontan.authentication.domain.usecases.LoginUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlin.test.Test
import kotlinx.coroutines.runBlocking

class LoginUseCaseTest {
    private val repository: IAuthRepository = mockk(relaxed = true)
    private val loginUseCase = LoginUseCase(repository)

    @Test
    fun testLogin(): Unit = runBlocking {
        coEvery { repository.login(any()) }.coAnswers { mockk() }
        loginUseCase.invoke(mockk())
        coVerify { repository.login(any()) }
    }
}