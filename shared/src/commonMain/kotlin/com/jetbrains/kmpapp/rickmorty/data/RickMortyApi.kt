package com.jetbrains.kmpapp.rickmorty.data

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

// Para guardar un secreto, lo pondría en local.properties (fuera de Git) y lo pasaría mediante BuildKonfig o BuildConfig.
class RickMortyApi(private val client: HttpClient) {
    suspend fun personajes(pagina: Int): CharacterPageDto {
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

    companion object {
        fun createDefault(): HttpClient {
            return HttpClient {
                install(ContentNegotiation) {
                    json(Json {
                        ignoreUnknownKeys = true
                    })
                }
                defaultRequest {
                    url("https://rickandmortyapi.com/api/")
                }
            }
        }
    }
}
