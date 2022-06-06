package com.lek.spontan

import com.squareup.sqldelight.db.SqlDriver
import io.ktor.client.HttpClient

expect class Platform() {
    val platform: String
}

expect val httpClient: HttpClient

expect object DatabaseDriverFactory {
    fun createDriver(): SqlDriver
}