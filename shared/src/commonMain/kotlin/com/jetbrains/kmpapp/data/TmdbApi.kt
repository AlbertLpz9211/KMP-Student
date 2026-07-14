package com.jetbrains.kmpapp.data

import com.jetbrains.kmpapp.data.dto.MovieDetailDto
import com.jetbrains.kmpapp.data.dto.MoviePageDto
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class TmdbApi(private val apiKey: String, engine: HttpClientEngine? = null) {
    private val client = if (engine != null) HttpClient(engine) {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
        defaultRequest {
            url("https://api.themoviedb.org/3/")
            url.parameters.append("api_key", apiKey)
            url.parameters.append("language", "es-MX")
        }
    } else HttpClient {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
        defaultRequest {
            url("https://api.themoviedb.org/3/")
            url.parameters.append("api_key", apiKey)
            url.parameters.append("language", "es-MX")
        }
    }

    suspend fun populares(pagina: Int = 1): MoviePageDto =
        client.get("movie/popular") {
            parameter("page", pagina)
        }.body()

    suspend fun mejorValoradas(pagina: Int = 1): MoviePageDto =
        client.get("movie/top_rated") {
            parameter("page", pagina)
        }.body()

    suspend fun enCartelera(pagina: Int = 1): MoviePageDto =
        client.get("movie/now_playing") {
            parameter("page", pagina)
        }.body()

    suspend fun detalle(id: Int): MovieDetailDto =
        client.get("movie/$id").body()

    suspend fun buscar(query: String, pagina: Int = 1): MoviePageDto =
        client.get("search/movie") {
            parameter("query", query)
            parameter("page", pagina)
        }.body()
}
