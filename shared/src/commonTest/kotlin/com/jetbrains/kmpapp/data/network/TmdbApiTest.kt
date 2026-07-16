package com.jetbrains.kmpapp.data.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class TmdbApiTest {

    private fun createMockClient(
        mockResponse: String,
        status: HttpStatusCode = HttpStatusCode.OK,
        throwError: Boolean = false
    ): HttpClient {
        val mockEngine = MockEngine { _ ->
            if (throwError) throw Exception("Error de red")
            respond(
                content = mockResponse,
                status = status,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }
        return HttpClient(mockEngine) {
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true })
            }
        }
    }

    // 1. Éxito: getTopRated
    @Test
    fun test1_getTopRated_Success() = runTest {
        val json = """{"results": [{"id": 1, "title": "Peli Top", "overview": "Genial", "vote_average": 9.0, "poster_path": "/top.jpg", "release_date": "2026-05-10"}]}"""
        val api = TmdbApi(createMockClient(json))
        val response = api.getTopRated()
        assertEquals(1, response.results.size)
    }

    // 2. JSON Vacío: getTopRated
    @Test
    fun test2_getTopRated_Empty() = runTest {
        val json = """{"results": []}"""
        val api = TmdbApi(createMockClient(json))
        val response = api.getTopRated()
        assertTrue(response.results.isEmpty())
    }

    // 3. Éxito: getNowPlaying
    @Test
    fun test3_getNowPlaying_Success() = runTest {
        val json = """{"results": [{"id": 2, "title": "Peli Now", "overview": "Nueva", "vote_average": 8.0, "poster_path": "/now.jpg", "release_date": "2026-07-15"}]}"""
        val api = TmdbApi(createMockClient(json))
        val response = api.getNowPlaying()
        assertEquals(1, response.results.size)
    }

    // 4. Éxito: getMovieDetail
    @Test
    fun test4_getMovieDetail_Success() = runTest {
        val json = """{"id": 100, "title": "Detalle Peli", "overview": "Info"}"""
        val api = TmdbApi(createMockClient(json))
        val response = api.getMovieDetail(100)
        assertEquals(100, response.id)
    }

    // 5. Éxito: searchMovies
    @Test
    fun test5_searchMovies_Success() = runTest {
        val json = """{"results": [{"id": 3, "title": "Matrix", "overview": "Sci-Fi", "vote_average": 9.5, "poster_path": "/m.jpg", "release_date": "1999-03-31"}]}"""
        val api = TmdbApi(createMockClient(json))
        val response = api.searchMovies("Matrix")
        assertEquals("Matrix", response.results[0].title)
    }

    // 6. JSON Vacío: searchMovies
    @Test
    fun test6_searchMovies_Empty() = runTest {
        val json = """{"results": []}"""
        val api = TmdbApi(createMockClient(json))
        val response = api.searchMovies("Nada")
        assertTrue(response.results.isEmpty())
    }

    // 7. Error 401
    @Test
    fun test7_api_Error401() = runTest {
        val api = TmdbApi(createMockClient("""{"status": "error"}""", HttpStatusCode.Unauthorized))
        assertFailsWith<Exception> { api.getTopRated() }
    }

    // 8. Error de Red
    @Test
    fun test8_api_NetworkError() = runTest {
        val api = TmdbApi(createMockClient("", throwError = true))
        assertFailsWith<Exception> { api.getNowPlaying() }
    }
}