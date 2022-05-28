package com.lek.spontan.android.presentation.authentication

import androidx.lifecycle.viewModelScope
import com.lek.spontan.android.presentation.authentication.signup.SignUpViewEvent
import com.lek.spontan.android.presentation.authentication.signup.SignUpViewState
import com.lek.spontan.android.presentation.shared.BaseViewModel
import com.lek.spontan.authentication.domain.models.SignUpRequestModel
import com.lek.spontan.authentication.domain.usecases.SignUpUseCase
import com.lek.spontan.di.DI
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignupViewModel(
    private val signUpUseCase: SignUpUseCase = DI.signupUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : BaseViewModel<SignUpViewState, SignUpViewEvent>() {

    override fun createInitialState(): SignUpViewState = SignUpViewState.EMPTY

    private fun performSignUp() {
        setState { copy(isLoading = true, error = "") }
        viewModelScope.launch(dispatcher) {
            kotlin.runCatching {
                signUpUseCase(currentState.toRequest())
            }.onSuccess {
                setState {
                    copy(
                        accessToken = it.accessToken,
                        isSignUpSuccess = true,
                        error = "",
                        isLoading = false
                    )
                }
            }.onFailure {
                setState { copy(error = it.message ?: "Signup Failed", isLoading = false) }
            }
        }
    }

    override fun onEvent(event: SignUpViewEvent) {
        when (event) {
            is SignUpViewEvent.NameTyped -> setState { copy(name = event.name) }
            is SignUpViewEvent.EmailTyped -> setState { copy(email = event.email) }
            is SignUpViewEvent.PasswordTyped -> setState { copy(password = event.password) }
            is SignUpViewEvent.SignupButtonClicked -> performSignUp()
        }
    }

    private fun SignUpViewState.toRequest() = SignUpRequestModel(
        name = name,
        email = email,
        password = password
    )
}