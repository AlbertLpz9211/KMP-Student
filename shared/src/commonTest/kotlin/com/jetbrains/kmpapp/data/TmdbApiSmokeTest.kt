package com.jetbrains.kmpapp.data

import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class TmdbApiSmokeTest {

    @Test
    fun testPopularesParseo() = runTest {
        val mockEngine = MockEngine { request ->
            respond(
                content = """
                    {
                        "page": 1,
                        "results": [
                            {
                                "id": 1,
                                "title": "Dune",
                                "overview": "Test overview",
                                "vote_average": 8.5,
                                "poster_path": "/test.jpg",
                                "release_date": "2024-01-01"
                            }
                        ],
                        "total_pages": 10
                    }
                """.trimIndent(),
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }

        val api = TmdbApi(apiKey = "fake_key", engine = mockEngine)
        val result = api.populares(1)

        assertEquals(1, result.page, "La página debería ser 1")
        assertEquals(1, result.results.size, "Debería haber 1 película en los resultados")
        assertEquals("Dune", result.results[0].title, "El título de la película debería ser Dune")
        assertEquals(10, result.totalPages, "El total de páginas debería ser 10")
    }
}
