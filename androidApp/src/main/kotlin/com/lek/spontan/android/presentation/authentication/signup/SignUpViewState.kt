package com.lek.spontan.android.presentation.authentication.signup

data class SignUpViewState(
    val name: String,
    val email: String,
    val password: String,
    val isLoading: Boolean,
    val isSignUpSuccess: Boolean,
    val accessToken: String,
    val error: String,
    val userHasAccount: Boolean
) {
    val isInputValid: Boolean
        get() = name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()

    companion object {
        val EMPTY = SignUpViewState(
            name = "",
            email = "",
            password = "",
            isLoading = false,
            isSignUpSuccess = false,
            accessToken = "",
            error = "",
            userHasAccount = false
        )
    }
}

sealed interface SignUpViewEvent {
    data class NameTyped(val name: String) : SignUpViewEvent
    data class EmailTyped(val email: String) : SignUpViewEvent
    data class PasswordTyped(val password: String) : SignUpViewEvent
    object SignupButtonClicked : SignUpViewEvent
}