package com.jetbrains.kmpapp.data

import io.ktor.client.engine.mock.*
import io.ktor.http.*
import io.ktor.utils.io.errors.*
import kotlinx.coroutines.test.runTest
import kotlin.test.*

class RickMortyApiSmokeTest {

    private fun createMockApi(status: HttpStatusCode, content: String = ""): RickMortyApi {
        val engine = MockEngine {
            respond(
                content = content,
                status = status,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }
        return RickMortyApi(engine = engine)
    }

    @Test
    fun testPersonajesExitoConDosPersonajes() = runTest {
        val json = """
            {
                "info": {"count": 2, "pages": 1, "next": null, "prev": null},
                "results": [
                    {"id": 1, "name": "Rick Sanchez", "status": "Alive", "species": "Human", "type": "", "gender": "Male", "image": "rick.jpg"},
                    {"id": 2, "name": "Morty Smith", "status": "Alive", "species": "Human", "type": "", "gender": "Male", "image": "morty.jpg"}
                ]
            }
        """.trimIndent()
        val api = createMockApi(HttpStatusCode.OK, json)
        val result = api.personajes(1)
        
        assertEquals(2, result.results.size)
        assertEquals("Rick Sanchez", result.results[0].name)
        assertEquals("Morty Smith", result.results[1].name)
    }

    @Test
    fun testPersonajesListaVacia() = runTest {
        val json = """{"info":{"count":0,"pages":0,"next":null,"prev":null},"results":[]}"""
        val api = createMockApi(HttpStatusCode.OK, json)
        val result = api.personajes()
        
        assertTrue(result.results.isEmpty())
        assertEquals(0, result.info.count)
    }

    @Test
    fun testPersonajesError404() = runTest {
        val api = createMockApi(HttpStatusCode.NotFound, """{"error":"There is nothing here"}""")
        
        // Ktor body() throws exception on non-2xx by default if not handled, 
        // or we check how body() behaves. Usually it throws NoTransformationFoundException or similar if body is not expected type or ClientRequestException
        assertFails {
            api.personajes()
        }
    }

    @Test
    fun testPersonajeDetalleExito() = runTest {
        val json = """{"id":1,"name":"Rick Sanchez","status":"Alive","species":"Human","type":"","gender":"Male","image":"img.jpg"}"""
        val api = createMockApi(HttpStatusCode.OK, json)
        val result = api.personaje(1)
        assertEquals(1, result.id)
        assertEquals("Rick Sanchez", result.name)
    }

    @Test
    fun testErrorDeRed() = runTest {
        val engine = MockEngine {
            throw IOException("No internet")
        }
        val api = RickMortyApi(engine = engine)
        assertFailsWith<IOException> { api.personajes() }
    }
}
