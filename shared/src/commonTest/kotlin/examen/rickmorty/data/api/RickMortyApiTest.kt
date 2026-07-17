package examen.rickmorty.data.api

import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

class RickMortyApiTest {

    private fun createMockApi(handler: suspend () -> String, status: HttpStatusCode = HttpStatusCode.OK): RickMortyApi {
        val mockEngine = MockEngine { _ ->
            respond(
                content = handler(),
                status = status,
                headers = headersOf("Content-Type", "application/json")
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
    fun `test personajes returns characters on success`() = runTest {
        val json = """
            {
              "info": { "count": 2, "pages": 1 },
              "results": [
                { "id": 1, "name": "Rick", "status": "Alive", "species": "Human", "image": "url", "location": { "name": "Earth", "url": "url" } },
                { "id": 2, "name": "Morty", "status": "Alive", "species": "Human", "image": "url", "location": { "name": "Earth", "url": "url" } }
              ]
            }
        """.trimIndent()
        
        val api = createMockApi({ json })
        val result = api.personajes(1)
        
        assertEquals(2, result.results.size)
        assertEquals("Rick", result.results[0].name)
        assertEquals("Morty", result.results[1].name)
    }

    @Test
    fun `test personajes returns empty list`() = runTest {
        val json = """
            {
              "info": { "count": 0, "pages": 0 },
              "results": []
            }
        """.trimIndent()
        
        val api = createMockApi({ json })
        val result = api.personajes(1)
        
        assertEquals(0, result.results.size)
    }

    @Test
    fun `test personajes fails on 404`() = runTest {
        val api = createMockApi({ "" }, HttpStatusCode.NotFound)
        
        assertFails {
            api.personajes(1)
        }
    }
}