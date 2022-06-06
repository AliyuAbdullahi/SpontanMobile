package com.lek.spontan.authentication.domain.models

data class LoginResponseDomainData(
    val email: String,
    val name: String,
    val accessToken: String,
    val error: String = ""
) {
    companion object {
        fun error(message: String) = LoginResponseDomainData(
            email = "",
            name = "",
            accessToken = "",
            error = message
        )
    }
}