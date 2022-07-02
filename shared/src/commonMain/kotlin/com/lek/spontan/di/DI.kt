package com.lek.spontan.di

import com.lek.spontan.authentication.data.AuthRepository
import com.lek.spontan.authentication.data.IUserDao
import com.lek.spontan.authentication.data.UserDao
import com.lek.spontan.authentication.data.UserRepository
import com.lek.spontan.authentication.domain.repository.IAuthRepository
import com.lek.spontan.authentication.domain.repository.IUserRepository
import com.lek.spontan.authentication.domain.usecases.AddUserUseCase
import com.lek.spontan.authentication.domain.usecases.DeleteUserUseCase
import com.lek.spontan.authentication.domain.usecases.GetUserUseCase
import com.lek.spontan.authentication.domain.usecases.LoginUseCase
import com.lek.spontan.authentication.domain.usecases.SignUpUseCase
import com.lek.spontan.data.NetworkInterface
import com.lek.spontan.data.createDatabase
import com.lek.spontan.events.data.EventDao
import com.lek.spontan.events.data.EventsRepository
import com.lek.spontan.events.data.IEventsDao
import com.lek.spontan.events.domain.CreateEventUseCase
import com.lek.spontan.events.domain.GetEventsUseCase
import com.lek.spontan.events.domain.IEventsRepository
import com.lek.spontan.events.domain.LoadEventsUseCase
import com.lek.spontan.httpClient

object DI {

    private val networkInterface = NetworkInterface(httpClient)
    private val repository: IAuthRepository by lazy { AuthRepository(networkInterface) }
    val loginUseCase: LoginUseCase by lazy { LoginUseCase(repository) }
    val signupUseCase: SignUpUseCase by lazy { SignUpUseCase(repository) }

    private val db = createDatabase()
    private val userDao: IUserDao by lazy { UserDao(db) }
    private val userRepo: IUserRepository by lazy { UserRepository(userDao) }

    private val eventDao: IEventsDao by lazy { EventDao(db) }
    private val eventRepo: IEventsRepository by lazy {
        EventsRepository(
            eventDao,
            networkInterface
        )
    }

    val getUserUseCase: GetUserUseCase by lazy { GetUserUseCase(userRepo) }
    val addUserUseCase: AddUserUseCase by lazy { AddUserUseCase(userRepo) }
    val deleteUserUseCase: DeleteUserUseCase by lazy { DeleteUserUseCase(userRepo) }

    val createEventUseCase: CreateEventUseCase by lazy { CreateEventUseCase(userRepo, eventRepo) }
    val getEventsUseCase: GetEventsUseCase by lazy { GetEventsUseCase(eventRepo) }
    val loadEventsUseCase: LoadEventsUseCase by lazy { LoadEventsUseCase(userRepo, eventRepo) }
}