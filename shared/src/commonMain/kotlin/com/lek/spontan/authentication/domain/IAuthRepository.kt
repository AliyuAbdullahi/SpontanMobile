package com.lek.spontan.authentication.domain

import com.lek.spontan.authentication.domain.models.LoginRequestModel
import com.lek.spontan.authentication.domain.models.LoginResponseDomainData
import com.lek.spontan.authentication.domain.models.SignUpRequestModel

interface IAuthRepository {
    suspend fun login(loginRequestModel: LoginRequestModel): LoginResponseDomainData
    suspend fun signup(signUpRequestModel: SignUpRequestModel): LoginResponseDomainData
}