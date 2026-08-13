package com.jetbrains.kmpapp.data.remote

import com.jetbrains.kmpapp.domain.model.AppError
import com.jetbrains.kmpapp.domain.model.Resultado
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
import kotlin.test.assertTrue

class ApiClientTest {

    private fun createMockClient(handler: suspend () -> String, status: HttpStatusCode = HttpStatusCode.OK): HttpClient {
        return HttpClient(MockEngine {
            respond(
                content = handler(),
                status = status,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }) {
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true })
            }
        }
    }

    @Test
    fun `buscarEnItunes retorna Exito cuando la respuesta es 200 OK`() = runTest {
        val jsonResponse = """
            {
                "resultCount": 1,
                "results": [
                    {
                        "trackId": 1,
                        "trackName": "Test Track"
                    }
                ]
            }
        """.trimIndent()

        val client = createMockClient({ jsonResponse })
        val apiClient = ApiClient(client)

        val result = apiClient.buscarEnItunes("test")

        assertTrue(result is Resultado.Exito)
        assertEquals(1, result.datos.size)
        assertEquals(1L, result.datos[0].trackId)
    }

    @Test
    fun `buscarEnItunes retorna Error HttpCliente cuando es 404`() = runTest {
        val client = createMockClient({ "" }, HttpStatusCode.NotFound)
        val apiClient = ApiClient(client)

        val result = apiClient.buscarEnItunes("test")

        assertTrue(result is Resultado.Error)
        assertEquals(AppError.HttpCliente, result.error)
    }

    @Test
    fun `buscarEnItunes retorna Error HttpServidor cuando es 500`() = runTest {
        val client = createMockClient({ "" }, HttpStatusCode.InternalServerError)
        val apiClient = ApiClient(client)

        val result = apiClient.buscarEnItunes("test")

        assertTrue(result is Resultado.Error)
        assertEquals(AppError.HttpServidor, result.error)
    }

    @Test
    fun `buscarEnItunes retorna Error Parseo cuando el JSON es invalido`() = runTest {
        val invalidJson = "{ malformed json }"
        val client = createMockClient({ invalidJson })
        val apiClient = ApiClient(client)

        val result = apiClient.buscarEnItunes("test")

        assertTrue(result is Resultado.Error)
        assertEquals(AppError.Parseo, result.error)
    }
}
