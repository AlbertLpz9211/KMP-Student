package com.jetbrains.kmpapp.data

import com.jetbrains.kmpapp.data.dto.MoviePageDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.URLProtocol
import io.ktor.http.path
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class TmdbApi(private val apiKey: String) {

    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }

        defaultRequest {
            url {
                protocol = URLProtocol.HTTPS
                host = "api.themoviedb.org"
                path("3/")
                parameters.append("api_key", apiKey)
                parameters.append("language", "es-MX")
            }
        }
    }

    suspend fun populares(pagina: Int = 1): MoviePageDto {
        return client.get("movie/popular") {
            parameter("page", pagina)
        }.body()
    }
}