package com.jetbrains.kmpapp.data.remote

import com.jetbrains.kmpapp.domain.error.AppError
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class OpenLibraryApiTest {

    private fun createMockClient(handler: suspend () -> String, status: HttpStatusCode = HttpStatusCode.OK): HttpClient {
        return HttpClient(MockEngine {
            respond(
                content = handler(),
                status = status,
                headers = headersOf("Content-Type", ContentType.Application.Json.toString())
            )
        }) {
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true })
            }
        }
    }

    @Test
    fun `searchBooks success returns results`() = runTest {
        val json = """{
            "numFound": 1,
            "docs": [{ "key": "/works/OL27326W", "title": "The Lord of the Rings" }]
        }"""
        val api = OpenLibraryApi(createMockClient({ json }))
        val result = api.searchBooks("test")
        assertTrue(result.isSuccess)
        assertEquals(1, result.getOrNull()?.numFound)
    }

    @Test
    fun `searchBooks empty returns empty list`() = runTest {
        val json = """{ "numFound": 0, "docs": [] }"""
        val api = OpenLibraryApi(createMockClient({ json }))
        val result = api.searchBooks("test")
        assertTrue(result.isSuccess)
        assertEquals(0, result.getOrNull()?.docs?.size)
    }

    @Test
    fun `searchBooks degenerate handles missing fields`() = runTest {
        val json = """{ "numFound": 1, "docs": [{ "key": "1", "title": "T" }] }"""
        val api = OpenLibraryApi(createMockClient({ json }))
        val result = api.searchBooks("test")
        assertTrue(result.isSuccess)
        assertEquals(null, result.getOrNull()?.docs?.first()?.cover_i)
    }

    @Test
    fun `getWorkDetail success returns work`() = runTest {
        val json = """{ "key": "OL27326W", "title": "The Lord of the Rings" }"""
        val api = OpenLibraryApi(createMockClient({ json }))
        val result = api.getWorkDetail("OL27326W")
        assertTrue(result.isSuccess)
        assertEquals("The Lord of the Rings", result.getOrNull()?.title)
    }

    @Test
    fun `getWorkDetail degenerate handles missing description`() = runTest {
        val json = """{ "key": "OL123W", "title": "M" }"""
        val api = OpenLibraryApi(createMockClient({ json }))
        val result = api.getWorkDetail("OL123W")
        assertTrue(result.isSuccess)
        assertEquals(null, result.getOrNull()?.description)
    }

    @Test
    fun `api returns HttpClient error on 404`() = runTest {
        val api = OpenLibraryApi(createMockClient({ "" }, HttpStatusCode.NotFound))
        val result = api.getWorkDetail("404")
        assertTrue(result.isFailure)
        val error = (result.exceptionOrNull() as? AppErrorException)?.error
        assertTrue(error is AppError.HttpClient)
        assertEquals(404, (error as AppError.HttpClient).codigo)
    }

    @Test
    fun `api returns HttpServidor error on 500`() = runTest {
        val api = OpenLibraryApi(createMockClient({ "" }, HttpStatusCode.InternalServerError))
        val result = api.searchBooks("500")
        assertTrue(result.isFailure)
        val error = (result.exceptionOrNull() as? AppErrorException)?.error
        assertTrue(error is AppError.HttpServidor)
        assertEquals(500, (error as AppError.HttpServidor).codigo)
    }

    @Test
    fun `api returns Parseo error on invalid json`() = runTest {
        val api = OpenLibraryApi(createMockClient({ "{ invalid }" }))
        val result = api.searchBooks("parse")
        assertTrue(result.isFailure)
        val error = (result.exceptionOrNull() as? AppErrorException)?.error
        assertTrue(error is AppError.Parseo)
    }
}
