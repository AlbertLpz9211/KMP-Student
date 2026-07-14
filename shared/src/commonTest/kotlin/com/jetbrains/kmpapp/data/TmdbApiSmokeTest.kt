package com.jetbrains.kmpapp.data

import io.ktor.client.engine.mock.*
import io.ktor.http.*
import io.ktor.utils.io.errors.*
import kotlinx.coroutines.test.runTest
import kotlin.test.*

class TmdbApiSmokeTest {

    private fun createMockApi(status: HttpStatusCode, content: String = ""): TmdbApi {
        val engine = MockEngine {
            respond(
                content = content,
                status = status,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }
        return TmdbApi(apiKey = "fake_key", engine = engine)
    }

    @Test
    fun testPopularesExito() = runTest {
        val json = """{"page":1,"results":[{"id":1,"title":"Dune","overview":"Desc","vote_average":8.5,"poster_path":"/p.jpg","release_date":"2024-01-01"}],"total_pages":1}"""
        val api = createMockApi(HttpStatusCode.OK, json)
        val result = api.populares(1)
        assertEquals("Dune", result.results[0].title)
    }

    @Test
    fun testTopRatedExito() = runTest {
        val json = """{"page":1,"results":[{"id":2,"title":"Godfather","overview":"Desc","vote_average":9.2}],"total_pages":1}"""
        val api = createMockApi(HttpStatusCode.OK, json)
        val result = api.mejorValoradas(1)
        assertEquals("Godfather", result.results[0].title)
    }

    @Test
    fun testNowPlayingExito() = runTest {
        val json = """{"page":1,"results":[{"id":3,"title":"Deadpool","overview":"Desc","vote_average":7.8}],"total_pages":1}"""
        val api = createMockApi(HttpStatusCode.OK, json)
        val result = api.enCartelera(1)
        assertEquals("Deadpool", result.results[0].title)
    }

    @Test
    fun testDetalleExito() = runTest {
        val json = """{"id":1,"title":"Dune","overview":"Desc","vote_average":8.5,"runtime":155}"""
        val api = createMockApi(HttpStatusCode.OK, json)
        val result = api.detalle(1)
        assertEquals(155, result.runtime)
    }

    @Test
    fun testBuscarExito() = runTest {
        val json = """{"page":1,"results":[{"id":1,"title":"Batman","overview":"Desc","vote_average":8.0}],"total_pages":1}"""
        val api = createMockApi(HttpStatusCode.OK, json)
        val result = api.buscar("batman")
        assertEquals("Batman", result.results[0].title)
    }

    @Test
    fun testError401Invalido() = runTest {
        val api = createMockApi(HttpStatusCode.Unauthorized, """{"status_message":"Invalid API key"}""")
        assertFails { api.populares() }
    }

    @Test
    fun testJsonVacioNoExplota() = runTest {
        val api = createMockApi(HttpStatusCode.OK, """{"page":1,"results":[],"total_pages":0}""")
        val result = api.populares()
        assertTrue(result.results.isEmpty())
    }

    @Test
    fun testErrorDeRed() = runTest {
        val engine = MockEngine {
            throw IOException("No internet")
        }
        val api = TmdbApi(apiKey = "fake", engine = engine)
        assertFailsWith<IOException> { api.populares() }
    }
}
