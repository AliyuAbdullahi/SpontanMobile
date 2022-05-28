package com.lek.spontan.authentication.domain.models

data class LoginResponseDomainData(
    val email: String,
    val name: String,
    val accessToken: String
)