package com.lek.spontan

import io.ktor.client.HttpClient

expect class Platform() {
    val platform: String
}

expect val httpClient: HttpClient