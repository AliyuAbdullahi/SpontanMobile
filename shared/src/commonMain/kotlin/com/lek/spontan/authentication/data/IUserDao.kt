package com.lek.spontan.authentication.data

data class User(
    val name: String,
    val email: String,
    val accessToken: String?,
    val photo: String?
)

interface IUserDao {
    fun saveUser(user: User)
    suspend fun getUser(): User?
    suspend fun deleteUser()
}