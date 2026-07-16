package com.jetbrains.kmpapp

import com.jetbrains.kmpapp.data.TmdbApi
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.ClientRequestException
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
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class TmdbApiSmokeTest {

    private val headersJson = headersOf(
        HttpHeaders.ContentType,
        ContentType.Application.Json.toString()
    )

    private val paginaJson = """
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
    """.trimIndent()

    private val paginaVaciaJson = """
        {
          "page": 1,
          "results": [],
          "total_pages": 0
        }
    """.trimIndent()

    private val detalleJson = """
        {
          "id": 1,
          "title": "Dune",
          "overview": "Película de ciencia ficción",
          "vote_average": 8.4,
          "poster_path": "/dune.jpg",
          "release_date": "2021-10-22"
        }
    """.trimIndent()

    private fun crearApi(
        contenido: String,
        estado: HttpStatusCode = HttpStatusCode.OK
    ): TmdbApi {
        val engine = MockEngine {
            respond(
                content = contenido,
                status = estado,
                headers = headersJson
            )
        }

        val client = HttpClient(engine) {
            expectSuccess = true

            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                })
            }

            defaultRequest {
                url("https://api.themoviedb.org/3/")
            }
        }

        return TmdbApi("prueba", client)
    }

    @Test
    fun populares_exito() = runBlocking {
        val respuesta = crearApi(paginaJson).populares()

        assertEquals("Dune", respuesta.results[0].title)
    }

    @Test
    fun topRated_exito() = runBlocking {
        val respuesta = crearApi(paginaJson).topRated()

        assertEquals(1, respuesta.results[0].id)
    }

    @Test
    fun nowPlaying_exito() = runBlocking {
        val respuesta = crearApi(paginaJson).nowPlaying()

        assertEquals(1, respuesta.totalPages)
    }

    @Test
    fun detalle_exito() = runBlocking {
        val respuesta = crearApi(detalleJson).detalle(1)

        assertEquals("Dune", respuesta.title)
    }

    @Test
    fun buscar_exito() = runBlocking {
        val respuesta = crearApi(paginaJson).buscar("Dune")

        assertEquals(1, respuesta.results.size)
    }

    @Test
    fun json_vacio() = runBlocking {
        val respuesta = crearApi(paginaVaciaJson).populares()

        assertTrue(respuesta.results.isEmpty())
    }

    @Test
    fun error_401() {
        runBlocking {
            val api = crearApi(
                contenido = """{"status_message":"API key incorrecta"}""",
                estado = HttpStatusCode.Unauthorized
            )

            assertFailsWith<ClientRequestException> {
                api.populares()
            }
        }
    }

    @Test
    fun error_de_red() {
        runBlocking {
            val engine = MockEngine {
                throw IllegalStateException("Sin internet")
            }

            val client = HttpClient(engine) {
                install(ContentNegotiation) {
                    json(Json {
                        ignoreUnknownKeys = true
                    })
                }
            }

            val api = TmdbApi("prueba", client)

            assertFailsWith<IllegalStateException> {
                api.populares()
            }
        }
    }
}