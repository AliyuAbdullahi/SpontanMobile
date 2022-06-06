package com.lek.spontan.android.presentation.authentication

import androidx.lifecycle.viewModelScope
import com.lek.spontan.android.presentation.authentication.signup.SignUpViewEvent
import com.lek.spontan.android.presentation.authentication.signup.SignUpViewState
import com.lek.spontan.android.presentation.shared.BaseViewModel
import com.lek.spontan.authentication.domain.models.LoginResponseDomainData
import com.lek.spontan.authentication.domain.models.SignUpRequestModel
import com.lek.spontan.authentication.domain.repository.UserDomainModel
import com.lek.spontan.authentication.domain.usecases.AddUserUseCase
import com.lek.spontan.authentication.domain.usecases.GetUserUseCase
import com.lek.spontan.authentication.domain.usecases.SignUpUseCase
import com.lek.spontan.di.DI
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignupViewModel(
    private val signUpUseCase: SignUpUseCase = DI.signupUseCase,
    private val getUserUseCase: GetUserUseCase = DI.getUserUseCase,
    private val addUserUseCase: AddUserUseCase = DI.addUserUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : BaseViewModel<SignUpViewState, SignUpViewEvent>() {

    override fun createInitialState(): SignUpViewState = SignUpViewState.EMPTY

    private fun checkHasUser(onHasUser: (Boolean) -> Unit) {
        viewModelScope.launch {
            val user = getUserUseCase()
            onHasUser(user != null)
        }
    }

    private fun performSignUp() {
        setState { copy(isLoading = true, error = "") }
        viewModelScope.launch(dispatcher) {
            kotlin.runCatching {
                signUpUseCase(currentState.toRequest())
            }.onSuccess {
                setState {
                    handleSuccess(it)
                }.also {
                    updateStateAfterSignUp()
                }
            }.onFailure {
                setState { copy(error = it.message ?: "Signup Failed", isLoading = false) }
            }
        }
    }

    private suspend fun updateStateAfterSignUp() {
        if (currentState.error.isEmpty()) {
            addUserUseCase(
                UserDomainModel(
                    name = currentState.name,
                    email = currentState.email,
                    accessToken = currentState.accessToken,
                    photo = ""
                )
            )
            setState { copy(userHasAccount = true) }
        }
    }

    private fun SignUpViewState.handleSuccess(
        domainData: LoginResponseDomainData
    ) = if (domainData.error.isNotEmpty()) {
        copy(error = domainData.error, isLoading = false)
    } else {
        copy(
            accessToken = accessToken,
            isSignUpSuccess = true,
            error = "",
            isLoading = false
        )
    }

    override fun onEvent(event: SignUpViewEvent) {
        when (event) {
            is SignUpViewEvent.NameTyped -> setState { copy(name = event.name, error = "") }
            is SignUpViewEvent.EmailTyped -> setState { copy(email = event.email, error = "") }
            is SignUpViewEvent.PasswordTyped -> setState { copy(password = event.password, error = "") }
            is SignUpViewEvent.SignupButtonClicked -> performSignUp()
        }
    }

    private fun SignUpViewState.toRequest() = SignUpRequestModel(
        name = name,
        email = email,
        password = password
    )

    init {
        checkHasUser { setState { copy(userHasAccount = it) } }
    }
}