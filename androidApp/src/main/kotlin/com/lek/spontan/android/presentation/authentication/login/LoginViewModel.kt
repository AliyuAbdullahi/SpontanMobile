package com.lek.spontan.android.presentation.authentication.login

import androidx.lifecycle.viewModelScope
import com.lek.spontan.android.presentation.shared.BaseViewModel
import com.lek.spontan.authentication.domain.models.LoginRequestModel
import com.lek.spontan.authentication.domain.usecases.LoginUseCase
import com.lek.spontan.di.DI
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(
    private val useCase: LoginUseCase = DI.loginUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) :
    BaseViewModel<LoginViewState, LoginViewEvent>() {

    override fun createInitialState(): LoginViewState = LoginViewState.EMPTY

    private fun performLogin() {
        viewModelScope.launch(dispatcher) {
            kotlin.runCatching {
                setState { copy(isLoading = true, error = "") }
                useCase(buildRequest())
            }.onSuccess {
                setState {
                    copy(
                        accessToken = it.accessToken,
                        name = it.name,
                        error = "",
                        isLoading = false,
                        isLoginSuccess = true
                    )
                }
            }.onFailure {
                setState { copy(error = it.message.toString(), isLoading = false) }
            }
        }
    }

    private fun buildRequest(): LoginRequestModel =
        currentState.let { LoginRequestModel(it.email, it.password) }

    override fun onEvent(event: LoginViewEvent) =
        when (event) {
            is LoginViewEvent.EmailTyped -> setState { copy(email = event.email) }
            is LoginViewEvent.PasswordTyped -> setState { copy(password = event.password) }
            is LoginViewEvent.LoginButtonClick -> performLogin()
        }
}