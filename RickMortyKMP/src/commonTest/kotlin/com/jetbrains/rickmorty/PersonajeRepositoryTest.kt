package com.jetbrains.rickmorty

import com.jetbrains.rickmorty.api.RickMortyApi
import com.jetbrains.rickmorty.db.CharacterLocalDataSource
import com.jetbrains.rickmorty.db.RickMortyDatabase
import com.jetbrains.rickmorty.db.createTestDriver
import io.ktor.client.*
import io.ktor.client.engine.mock.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals

class PersonajeRepositoryTest {

    @Test
    fun testRefrescarYObservar() = runTest {
        // Setup Fake API
        val json = """
            {
                "info": { "count": 1, "pages": 1, "next": null, "prev": null },
                "results": [
                    { "id": 1, "name": "Rick", "status": "Alive", "species": "Human", "gender": "Male", "image": "url", "location": { "name": "Earth", "url": "url" } }
                ]
            }
        """.trimIndent()
        
        val mockEngine = MockEngine { _ ->
            respond(
                content = json,
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }
        val client = HttpClient(mockEngine) {
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true })
            }
        }
        val api = RickMortyApi(client)

        // Setup DB in memory
        val driver = createTestDriver()
        val database = RickMortyDatabase(driver)
        val localDataSource = CharacterLocalDataSource(database)

        val repository = PersonajeRepository(api, localDataSource)

        // Refrescar
        repository.refrescar()

        // Observar
        val personajes = repository.observar().first()
        
        assertEquals(1, personajes.size)
        assertEquals("Rick", personajes[0].nombre)
        
        driver.close()
    }
}
