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

actual class Platform actual constructor() {
    actual val platform: String = "Android ${android.os.Build.VERSION.SDK_INT}"
}

actual val httpClient: HttpClient = HttpClient() {
    install(Logging)
    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            isLenient = true
        })
    }
}

actual object DatabaseDriverFactory {
    lateinit var context: Context
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(SpontanDatabase.Schema, context, "spontan.db")
    }
}