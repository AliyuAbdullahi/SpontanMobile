package com.lek.spontan.authentication.data

import com.lek.spontan.authentication.domain.repository.IAuthRepository
import com.lek.spontan.authentication.domain.models.LoginRequestModel
import com.lek.spontan.authentication.domain.models.LoginResponse
import com.lek.spontan.authentication.domain.models.LoginResponseDomainData
import com.lek.spontan.authentication.domain.models.SignUpRequestModel
import com.lek.spontan.data.NetworkInterface
import com.lek.spontan.data.Routes

class AuthRepository(private val networkInterface: NetworkInterface) : IAuthRepository {

    override suspend fun login(loginRequestModel: LoginRequestModel): LoginResponseDomainData =
        try {
            networkInterface.post<LoginRequestModel, LoginResponse>(
                Routes.LOGIN,
                loginRequestModel
            ).toDomainModel()
        } catch (error: Throwable) {
            LoginResponseDomainData.error(error.message.toString())
        }

    override suspend fun signup(signUpRequestModel: SignUpRequestModel): LoginResponseDomainData =
        try {
            networkInterface.post<SignUpRequestModel, LoginResponse>(
                Routes.SIGN_UP,
                signUpRequestModel
            ).toDomainModel()
        } catch (error: Throwable) {
            LoginResponseDomainData.error(error.message.toString())
        }
}

fun LoginResponse.toDomainModel() = LoginResponseDomainData(
    email = email,
    name = name,
    accessToken = accessToken
)