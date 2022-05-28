package com.lek.spontan.authentication.data

import com.lek.spontan.authentication.domain.IAuthRepository
import com.lek.spontan.authentication.domain.models.LoginRequestModel
import com.lek.spontan.authentication.domain.models.LoginResponse
import com.lek.spontan.authentication.domain.models.LoginResponseDomainData
import com.lek.spontan.authentication.domain.models.SignUpRequestModel
import com.lek.spontan.data.NetworkInterface
import com.lek.spontan.data.Routes

class AuthRepository(private val networkInterface: NetworkInterface) : IAuthRepository {

    override suspend fun login(loginRequestModel: LoginRequestModel): LoginResponseDomainData =
        networkInterface.post<LoginRequestModel, LoginResponse>(
            Routes.LOGIN,
            loginRequestModel
        ).toDomainModel()

    override suspend fun signup(signUpRequestModel: SignUpRequestModel): LoginResponseDomainData =
        networkInterface.post<SignUpRequestModel, LoginResponse>(
            Routes.SIGN_UP,
            signUpRequestModel
        ).toDomainModel()
}

fun LoginResponse.toDomainModel() = LoginResponseDomainData(
    email = email,
    name = name,
    accessToken = accessToken
)