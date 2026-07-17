package com.jetbrains.rickmorty

import com.jetbrains.rickmorty.api.RickMortyApi
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

    private fun createMockApi(content: String, status: HttpStatusCode = HttpStatusCode.OK): RickMortyApi {
        val mockEngine = MockEngine { _ ->
            respond(
                content = content,
                status = status,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }
        val client = HttpClient(mockEngine) {
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true })
            }
        }
        return RickMortyApi(client)
    }

    @Test
    fun testPersonajesExito() = runTest {
        val json = """
            {
                "info": { "count": 2, "pages": 1, "next": null, "prev": null },
                "results": [
                    { "id": 1, "name": "Rick", "status": "Alive", "species": "Human", "gender": "Male", "image": "url1", "location": { "name": "Earth", "url": "url" } },
                    { "id": 2, "name": "Morty", "status": "Alive", "species": "Human", "gender": "Male", "image": "url2", "location": { "name": "Earth", "url": "url" } }
                ]
            }
        """.trimIndent()
        
        val api = createMockApi(json)
        val response = api.personajes(1)
        
        assertEquals(2, response.results.size)
        assertEquals("Rick", response.results[0].name)
        assertEquals("Morty", response.results[1].name)
    }

    @Test
    fun testListaVacia() = runTest {
        val json = """
            {
                "info": { "count": 0, "pages": 0, "next": null, "prev": null },
                "results": []
            }
        """.trimIndent()
        
        val api = createMockApi(json)
        val response = api.personajes(1)
        
        assertEquals(0, response.results.size)
    }

    @Test
    fun testError404() = runTest {
        val api = createMockApi("Not Found", HttpStatusCode.NotFound)
        
        assertFails {
            api.personaje(999)
        }
    }
}
