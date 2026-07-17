package com.jetbrains.kmpapp.data

import com.jetbrains.kmpapp.data.dto.CharacterDto
import com.jetbrains.kmpapp.data.dto.CharacterPageDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class RickMortyApi(engine: HttpClientEngine? = null) {
    private val client = (engine?.let { HttpClient(it) } ?: HttpClient()).config {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
        defaultRequest {
            url("https://rickandmortyapi.com/api/")
        }
    }

    suspend fun personajes(pagina: Int = 1): CharacterPageDto =
        client.get("character") {
            parameter("page", pagina)
        }.body()

    suspend fun personaje(id: Int): CharacterDto =
        client.get("character/$id").body()

    suspend fun buscar(nombre: String): CharacterPageDto =
        client.get("character") {
            parameter("name", nombre)
        }.body()
}
