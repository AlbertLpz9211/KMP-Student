package com.jetbrains.kmpapp.data.Network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import com.jetbrains.kmpapp.BuildConfig
import com.jetbrains.kmpapp.data.Network.MovieDetailDTO
import com.jetbrains.kmpapp.data.Network.MovieResponseDTO

class TmdbApi(private val client: HttpClient) {

    private val baseUrl = "https://api.themoviedb.org/3"
    private val defaultLanguage = "es-MX" // Idioma por defecto para los resultados

    // 1. Top Rated
    suspend fun getTopRated(): MovieResponseDTO {
        return client.get("$baseUrl/movie/top_rated") {
            parameter("api_key", BuildConfig.TMDB_API_KEY)
            parameter("language", defaultLanguage)
        }.body()
    }

    // 2. Now Playing
    suspend fun getNowPlaying(): MovieResponseDTO {
        return client.get("$baseUrl/movie/now_playing") {
            parameter("api_key", BuildConfig.TMDB_API_KEY)
            parameter("language", defaultLanguage)
        }.body()
    }

    // 3. Detalle por ID
    suspend fun getMovieDetail(id: Int): MovieDetailDTO {
        return client.get("$baseUrl/movie/$id") {
            parameter("api_key", BuildConfig.TMDB_API_KEY)
            parameter("language", defaultLanguage)
        }.body()
    }

    // 4. Buscar por Query
    suspend fun searchMovies(query: String): MovieResponseDTO {
        return client.get("$baseUrl/search/movie") {
            parameter("api_key", BuildConfig.TMDB_API_KEY)
            parameter("query", query)
            parameter("language", defaultLanguage)
            parameter("include_adult", false)
        }.body()
    }
}