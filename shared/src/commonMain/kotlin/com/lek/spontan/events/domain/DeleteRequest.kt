package com.lek.spontan.events.domain

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DeleteRequest(
    @SerialName("id")
    val id: String
)