package com.lek.spontan.authentication.data

import com.lek.spontan.authentication.domain.models.LoginRequestModel
import com.lek.spontan.authentication.domain.models.SignUpRequestModel
import com.lek.spontan.data.NetworkInterface
import kotlin.test.Test
import kotlin.test.assertTrue
import kotlinx.coroutines.runBlocking

class AuthRepositoryTest {

    private lateinit var networkInterface: NetworkInterface

    @Test
    fun testLoginSuccess(): Unit = runBlocking {
        networkInterface = NetworkInterface(
            client = testSuccessHttpClient(mockLoginResponse)
        )

        val testModel = LoginRequestModel(
            email = "testEmail",
            password = "testPassword"
        )

        val repository = AuthRepository(networkInterface)

        val response = repository.login(testModel)
        assertTrue { response.name == "jagunmolu" }
        assertTrue { response.error.isEmpty() }
    }

    @Test
    fun testLoginFailure(): Unit = runBlocking {
        networkInterface = NetworkInterface(
            client = testFailureHttpClient
        )

        val testModel = LoginRequestModel(
            email = "testEmail",
            password = "testPassword"
        )

        val repository = AuthRepository(networkInterface)

        val response = repository.login(testModel)
        assertTrue { response.error.isNotEmpty() }
    }

    @Test
    fun testSignUp(): Unit = runBlocking {
        networkInterface = NetworkInterface(
            client = testSuccessHttpClient(mockLoginResponse)
        )

        val testModel = SignUpRequestModel(
            email = "testEmail",
            password = "testPassword",
            name = "testName"
        )

        val repository = AuthRepository(networkInterface)

        val response = repository.signup(testModel)
        assertTrue { response.name == "jagunmolu" }
        assertTrue { response.error.isEmpty() }
    }

    @Test
    fun testSignUpFailed(): Unit = runBlocking {
        networkInterface = NetworkInterface(
            client = testFailureHttpClient
        )

        val testModel = SignUpRequestModel(
            email = "testEmail",
            password = "testPassword",
            name = "testName"
        )

        val repository = AuthRepository(networkInterface)

        val response = repository.signup(testModel)
        assertTrue { response.error.isNotEmpty() }
    }
}