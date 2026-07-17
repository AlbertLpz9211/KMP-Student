package com.jetbrains.kmpapp.rickmorty

import com.jetbrains.kmpapp.rickmorty.data.RickMortyApi
import io.ktor.client.*
import io.ktor.client.engine.mock.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

class RickMortyApiTest {

    private fun createMockApi(handler: MockRequestHandler): RickMortyApi {
        val client = HttpClient(MockEngine) {
            engine {
                addHandler(handler)
            }
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true })
            }
        }
        return RickMortyApi(client)
    }

    @Test
    fun personajesExitoCon2Personajes() = runTest {
        val json = """
            {
                "info": { "count": 2, "pages": 1, "next": null, "prev": null },
                "results": [
                    { "id": 1, "name": "Rick", "status": "Alive", "species": "Human", "image": "", "location": { "name": "Earth", "url": "" } },
                    { "id": 2, "name": "Morty", "status": "Alive", "species": "Human", "image": "", "location": { "name": "Earth", "url": "" } }
                ]
            }
        """.trimIndent()

        val api = createMockApi { _ ->
            respond(json, HttpStatusCode.OK, headersOf(HttpHeaders.ContentType, "application/json"))
        }

        val result = api.personajes(1)
        assertEquals(2, result.results.size)
        assertEquals("Rick", result.results[0].name)
    }

    @Test
    fun personajesListaVacia() = runTest {
        val json = """
            {
                "info": { "count": 0, "pages": 0, "next": null, "prev": null },
                "results": []
            }
        """.trimIndent()

        val api = createMockApi { _ ->
            respond(json, HttpStatusCode.OK, headersOf(HttpHeaders.ContentType, "application/json"))
        }

        val result = api.personajes(1)
        assertEquals(0, result.results.size)
    }

    @Test
    fun error404LanzaExcepcion() = runTest {
        val api = createMockApi { _ ->
            respond("Not Found", HttpStatusCode.NotFound)
        }

        assertFails {
            api.personaje(999)
        }
    }
}
