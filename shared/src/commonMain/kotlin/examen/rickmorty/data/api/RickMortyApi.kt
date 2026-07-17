package examen.rickmorty.data.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class RickMortyApi(private val client: HttpClient) {
    companion object {
        fun createDefaultClient(): HttpClient {
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
}