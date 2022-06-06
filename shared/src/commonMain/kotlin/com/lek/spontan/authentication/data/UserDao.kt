package com.lek.spontan.authentication.data

import com.lek.spontan.shared.cache.SpontanDatabase

class UserDao(private val db: SpontanDatabase) : IUserDao {

    override fun saveUser(user: User) {
        db.userTableQueries.insertUser(
            name = user.name,
            email = user.email,
            photo = user.photo,
            accessToken = user.accessToken
        )
    }

    override suspend fun getUser(): User? {
        return db.userTableQueries.getUser().executeAsOneOrNull()?.let { cacheUser ->
            User(
                name = cacheUser.name,
                email = cacheUser.email,
                accessToken = cacheUser.accessToken,
                photo = cacheUser.photo
            )
        }
    }

    override suspend fun deleteUser() {
        db.userTableQueries.deleteUser()
    }
}