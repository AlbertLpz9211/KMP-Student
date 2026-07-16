package com.jetbrains.kmpapp.data

import com.jetbrains.kmpapp.data.dto.MovieDto
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

class TmdbApi(
    apiKey: String,
    private val client: HttpClient = crearHttpClient(apiKey)
) {

    suspend fun populares(pagina: Int = 1): MoviePageDto {
        return client.get("movie/popular") {
            parameter("page", pagina)
        }.body()
    }

    suspend fun topRated(pagina: Int = 1): MoviePageDto {
        return client.get("movie/top_rated") {
            parameter("page", pagina)
        }.body()
    }

    suspend fun nowPlaying(pagina: Int = 1): MoviePageDto {
        return client.get("movie/now_playing") {
            parameter("page", pagina)
        }.body()
    }

    suspend fun detalle(id: Int): MovieDto {
        return client.get("movie/$id").body()
    }

    suspend fun buscar(query: String, pagina: Int = 1): MoviePageDto {
        return client.get("search/movie") {
            parameter("query", query)
            parameter("page", pagina)
        }.body()
    }
}

private fun crearHttpClient(apiKey: String): HttpClient {
    return HttpClient {
        expectSuccess = true

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
}