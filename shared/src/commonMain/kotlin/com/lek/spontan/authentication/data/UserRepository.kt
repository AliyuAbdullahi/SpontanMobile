package com.lek.spontan.authentication.data

import com.lek.spontan.authentication.domain.repository.IUserRepository
import com.lek.spontan.authentication.domain.repository.UserDomainModel

class UserRepository(
    private val userDao: IUserDao
) : IUserRepository {

    override suspend fun getUser(): UserDomainModel? = userDao.getUser()?.toDomainModel()

    override suspend fun saveUser(domainModel: UserDomainModel) =
        userDao.saveUser(domainModel.toDataModel())

    override suspend fun deleteUser() = userDao.deleteUser()
}

fun UserDomainModel.toDataModel(): User =
    User(
        name = name,
        email = email,
        photo = photo,
        accessToken = accessToken
    )

fun User.toDomainModel(): UserDomainModel = UserDomainModel(
    name = name,
    email = email,
    photo = photo,
    accessToken = accessToken
)