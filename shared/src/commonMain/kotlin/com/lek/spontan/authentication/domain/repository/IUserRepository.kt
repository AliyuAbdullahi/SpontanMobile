package com.lek.spontan.authentication.domain.repository

interface IUserRepository {
    suspend fun getUser(): UserDomainModel?
    suspend fun saveUser(domainModel: UserDomainModel)
    suspend fun deleteUser()
}

data class UserDomainModel(
    val name: String,
    val email: String,
    val accessToken: String?,
    val photo: String?
)