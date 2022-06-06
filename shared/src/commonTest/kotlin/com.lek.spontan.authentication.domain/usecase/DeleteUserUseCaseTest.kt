package com.lek.spontan.authentication.domain.usecase

import com.lek.spontan.authentication.domain.repository.IUserRepository
import com.lek.spontan.authentication.domain.usecases.DeleteUserUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlin.test.Test
import kotlinx.coroutines.runBlocking

class DeleteUserUseCaseTest {

    private val repository: IUserRepository = mockk(relaxed = true)
    private val useCase = DeleteUserUseCase(repository)

    @Test
    fun testDeleteUser(): Unit = runBlocking {
        coEvery { repository.deleteUser() }.coAnswers { mockk() }
        useCase()
        coVerify { repository.deleteUser() }
    }
}