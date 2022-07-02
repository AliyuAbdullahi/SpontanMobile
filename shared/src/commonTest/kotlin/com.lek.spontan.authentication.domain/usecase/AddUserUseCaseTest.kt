package com.lek.spontan.authentication.domain.usecase

import com.lek.spontan.authentication.domain.repository.IUserRepository
import com.lek.spontan.authentication.domain.repository.UserDomainModel
import com.lek.spontan.authentication.domain.usecases.AddUserUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlin.test.Test
import kotlinx.coroutines.runBlocking

class AddUserUseCaseTest {

    private val repository: IUserRepository = mockk(relaxed = true)
    private val useCase = AddUserUseCase(repository)

    @Test
    fun testAddUser(): Unit = runBlocking {
        coEvery { repository.saveUser(any()) }.coAnswers { mockk() }
        val user: UserDomainModel = mockk()
        useCase(user)
        coVerify { repository.saveUser(user) }
    }
}