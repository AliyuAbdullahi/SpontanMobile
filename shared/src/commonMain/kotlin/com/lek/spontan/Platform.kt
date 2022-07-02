package com.lek.spontan

import com.squareup.sqldelight.db.SqlDriver
import io.ktor.client.HttpClient

expect val httpClient: HttpClient

expect object DatabaseDriverFactory {
    fun createDriver(): SqlDriver
}