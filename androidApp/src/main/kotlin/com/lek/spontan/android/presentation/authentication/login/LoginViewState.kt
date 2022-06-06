package com.lek.spontan.android.presentation.authentication.login

data class LoginViewState(
    val email: String,
    val password: String,
    val accessToken: String,
    val name: String,
    val error: String,
    val isLoading: Boolean,
    val isLoginSuccess: Boolean,
    val hasLoggedIn: Boolean
) {

    companion object {
        val EMPTY = LoginViewState(
            email = "",
            password = "",
            accessToken = "",
            name = "",
            error = "",
            isLoading = false,
            isLoginSuccess = false,
            hasLoggedIn = false
        )
    }
}

fun LoginViewState.isValid(): Boolean = email.isNotEmpty() && password.isNotEmpty()

sealed interface LoginViewEvent {
    object LoginButtonClick : LoginViewEvent
    data class EmailTyped(val email: String) : LoginViewEvent
    data class PasswordTyped(val password: String): LoginViewEvent
}