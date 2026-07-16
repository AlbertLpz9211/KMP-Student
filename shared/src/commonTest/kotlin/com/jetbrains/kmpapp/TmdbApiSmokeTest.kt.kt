package com.jetbrains.kmpapp

import com.jetbrains.kmpapp.data.TmdbApi
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals

class TmdbApiSmokeTest {

    @Test
    fun parsea_peliculas_correctamente() = runBlocking {
        val engine = MockEngine {
            respond(
                content = """
                    {
                      "page": 1,
                      "results": [
                        {
                          "id": 1,
                          "title": "Dune",
                          "overview": "Película de ciencia ficción",
                          "vote_average": 8.4,
                          "poster_path": "/dune.jpg",
                          "release_date": "2021-10-22"
                        }
                      ],
                      "total_pages": 1
                    }
                """.trimIndent(),
                status = HttpStatusCode.OK,
                headers = headersOf(
                    HttpHeaders.ContentType,
                    ContentType.Application.Json.toString()
                )
            )
        }

        val client = HttpClient(engine) {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                })
            }

            defaultRequest {
                url("https://api.themoviedb.org/3/")
            }
        }

        val api = TmdbApi("prueba", client)
        val respuesta = api.populares()

        assertEquals(1, respuesta.results.size)
        assertEquals("Dune", respuesta.results[0].title)
    }
}