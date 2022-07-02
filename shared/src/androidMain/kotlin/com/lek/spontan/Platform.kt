package com.lek.spontan

import android.content.Context
import com.lek.spontan.shared.cache.SpontanDatabase
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

actual val httpClient: HttpClient = HttpClient() {
    install(Logging)
    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            isLenient = true
            ignoreUnknownKeys = true
        })
    }
}

actual object DatabaseDriverFactory {
    lateinit var context: Context
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(SpontanDatabase.Schema, context, "spontan.db")
    }
}