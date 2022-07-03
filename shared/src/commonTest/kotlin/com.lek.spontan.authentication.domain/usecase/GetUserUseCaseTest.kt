package com.lek.spontan.authentication.domain.usecase

import com.lek.spontan.authentication.domain.repository.IUserRepository
import com.lek.spontan.authentication.domain.repository.UserDomainModel
import com.lek.spontan.authentication.domain.usecases.GetUserUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlin.test.Test
import kotlin.test.assertTrue
import kotlinx.coroutines.runBlocking

class GetUserUseCaseTest {

    private val repository: IUserRepository = mockk(relaxed = true)
    private val useCase = GetUserUseCase(repository)

    @Test
    fun testGetUser(): Unit = runBlocking {
        val testUser: UserDomainModel = mockk {
            every { name }.returns("test")
        }
        coEvery { repository.getUser() }.coAnswers { testUser }
        val user = useCase()
        coVerify { repository.getUser() }
        assertTrue { user!!.name == "test"  }
    }
}