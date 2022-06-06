package com.lek.spontan.authentication.data

import com.lek.spontan.authentication.domain.repository.UserDomainModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlin.test.Test
import kotlin.test.assertTrue
import kotlinx.coroutines.runBlocking

class UserRepositoryTest {

    private val dao: IUserDao = mockk(relaxed = true)
    private val repository = UserRepository(dao)

    @Test
    fun testAddUser(): Unit = runBlocking {
        val user = UserDomainModel(
            name = "test",
            email = "testEmail",
            accessToken = null,
            photo = null
        )
        repository.saveUser(user)
        coVerify { dao.saveUser(user.toDataModel()) }
    }

    @Test
    fun testGetUser(): Unit = runBlocking {
        val user = User(
            name = "test",
            email = "testEmail",
            accessToken = null,
            photo = null
        )
        coEvery { dao.getUser() }.coAnswers { user }
        val testUser = repository.getUser()
        assertTrue { testUser == user.toDomainModel() }
    }

    @Test
    fun testDeleteUser(): Unit = runBlocking {
        repository.deleteUser()
        coVerify { dao.deleteUser() }
    }
}