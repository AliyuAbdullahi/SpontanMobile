package com.lek.spontan.authentication.data

import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import io.ktor.utils.io.ByteReadChannel

private val failureMockEngine = MockEngine { request ->
    respond(
        content = ByteReadChannel(""),
        status = HttpStatusCode.NotFound,
        headers = headersOf(HttpHeaders.ContentType, "application/json")
    )
}

val testFailureHttpClient = HttpClient(failureMockEngine) {
    install(ContentNegotiation) {
        json()
    }
}

fun testSuccessHttpClient(body: String): HttpClient {
    val engine = MockEngine { request ->
        respond(
            content = ByteReadChannel(body),
            status = HttpStatusCode.OK,
            headers = headersOf(HttpHeaders.ContentType, "application/json")
        )
    }

    return HttpClient(engine) {
        install(ContentNegotiation) {
            json()
        }
    }
}