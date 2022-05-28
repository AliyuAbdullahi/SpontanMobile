package com.lek.spontan.data

import com.lek.spontan.httpClient
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

const val BASE_URL = "https://spontan-backend.herokuapp.com/"

class NetworkInterface(val client: HttpClient = httpClient) {

    suspend inline fun <reified T : Any> get(path: String, accessToken: String? = null): T =
        httpClient.get("$BASE_URL$path") {
            if (accessToken != null) {
                headers { append("x-access-token", accessToken) }
            }
        }.body()

    suspend inline fun <reified Body : Any, reified Result> post(
        path: String,
        requestBody: Body,
        accessToken: String? = null
    ): Result = client.post(urlString = "$BASE_URL$path") {
        contentType(ContentType.Application.Json)
        setBody(requestBody)
        if (accessToken != null) {
            headers { append("x-access-token", accessToken) }
        }
    }.body()

    suspend inline fun <reified Result> delete(
        path: String,
        accessToken: String
    ): Result = client.delete(urlString = "$BASE_URL$path") {
        headers { append("x-access-token", accessToken) }
    }.body()

    suspend inline fun <reified Body : Any, reified Result> delete(
        path: String,
        requestBody: Body,
        accessToken: String
    ): Result = client.delete(urlString = "$BASE_URL$path") {
        headers { append("x-access-token", accessToken) }
        setBody(requestBody)
    }.body()
}