package com.jetbrains.rickmorty.api

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

// Los secretos se guardarían en local.properties (fuera de Git) y se pasarían vía BuildKonfig o BuildConfig
class RickMortyApi(private val client: HttpClient) {
    
    constructor() : this(HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
        defaultRequest {
            url("https://rickandmortyapi.com/api/")
        }
    })

    suspend fun personajes(pagina: Int = 1): CharacterPageDto {
        return client.get("character") {
            parameter("page", pagina)
        }.body()
    }

    suspend fun personaje(id: Int): CharacterDto {
        return client.get("character/$id").body()
    }

    suspend fun buscar(nombre: String): CharacterPageDto {
        return client.get("character") {
            parameter("name", nombre)
        }.body()
    }
}
