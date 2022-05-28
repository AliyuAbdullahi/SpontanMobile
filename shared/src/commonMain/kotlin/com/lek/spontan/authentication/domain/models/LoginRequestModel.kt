package com.lek.spontan.authentication.domain.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginRequestModel(
    @SerialName("email")
    val email: String,
    @SerialName("password")
    val password: String
)