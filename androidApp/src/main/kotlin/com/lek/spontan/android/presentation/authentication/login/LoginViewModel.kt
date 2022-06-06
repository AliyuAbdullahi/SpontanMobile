package com.lek.spontan.android.presentation.authentication.login

import androidx.lifecycle.viewModelScope
import com.lek.spontan.android.presentation.shared.BaseViewModel
import com.lek.spontan.authentication.domain.models.LoginRequestModel
import com.lek.spontan.authentication.domain.models.LoginResponseDomainData
import com.lek.spontan.authentication.domain.repository.UserDomainModel
import com.lek.spontan.authentication.domain.usecases.AddUserUseCase
import com.lek.spontan.authentication.domain.usecases.GetUserUseCase
import com.lek.spontan.authentication.domain.usecases.LoginUseCase
import com.lek.spontan.di.DI
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(
    private val useCase: LoginUseCase = DI.loginUseCase,
    private val getUserUseCase: GetUserUseCase = DI.getUserUseCase,
    private val addUserUseCase: AddUserUseCase = DI.addUserUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : BaseViewModel<LoginViewState, LoginViewEvent>() {

    override fun createInitialState(): LoginViewState = LoginViewState.EMPTY

    private fun checkIfUserIsAlreadyIn(onHasUser: (Boolean) -> Unit) {
        viewModelScope.launch {
            val user = getUserUseCase()
            onHasUser(user != null)
        }
    }

    private fun performLogin() {
        viewModelScope.launch(dispatcher) {
            kotlin.runCatching {
                setState { copy(isLoading = true, error = "") }
                useCase(buildRequest())
            }.onSuccess {
                setState { handleSuccess(it) }
                    .also { updateStateAfterLogin() }
            }.onFailure {
                setState { copy(error = it.message.toString(), isLoading = false) }
            }
        }
    }

    private suspend fun updateStateAfterLogin() {
        if (currentState.error.isEmpty()) {
            addUserUseCase(
                UserDomainModel(
                    name = currentState.name,
                    email = currentState.email,
                    accessToken = currentState.accessToken,
                    photo = ""
                )
            )
            setState { copy(hasLoggedIn = true) }
        }
    }

    private fun LoginViewState.handleSuccess(
        domainData: LoginResponseDomainData
    ) = if (domainData.error.isNotEmpty()) {
        copy(error = domainData.error, isLoading = false)
    } else {
        copy(
            accessToken = domainData.accessToken,
            name = domainData.name,
            error = "",
            isLoading = false,
            isLoginSuccess = true
        )
    }

    private fun buildRequest(): LoginRequestModel =
        currentState.let { LoginRequestModel(it.email, it.password) }

    override fun onEvent(event: LoginViewEvent) =
        when (event) {
            is LoginViewEvent.EmailTyped -> setState { copy(email = event.email, error = "") }
            is LoginViewEvent.PasswordTyped -> setState { copy(password = event.password, error = "") }
            is LoginViewEvent.LoginButtonClick -> performLogin()
        }

    init {
        checkIfUserIsAlreadyIn {
            setState { copy(hasLoggedIn = it) }
        }
    }
}