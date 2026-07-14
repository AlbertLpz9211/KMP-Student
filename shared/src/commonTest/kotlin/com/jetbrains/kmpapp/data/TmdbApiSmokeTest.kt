package com.jetbrains.kmpapp.data

import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.engine.mock.respondError
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertTrue

// Esta clase contiene las pruebas (tests) para verificar que la conexión con el servidor funciona.
// Las pruebas ayudan a detectar errores sin tener que abrir la aplicación en un celular.
class TmdbApiSmokeTest {

    // Simulamos una respuesta JSON exitosa para usarla en diferentes pruebas.
    private val jsonExito = """
        {
            "page": 1,
            "results": [{"id": 1, "title": "Peli 1", "overview": "...", "vote_average": 7.0}],
            "total_pages": 1
        }
    """.trimIndent()

    // Prueba 1: Verificar que podemos obtener y leer películas populares.
    @Test
    fun testPopularesExito() = runTest {
        // Configuramos un motor de prueba que siempre devuelve éxito (OK 200) y nuestro JSON de ejemplo.
        val engine = MockEngine { respond(jsonExito, HttpStatusCode.OK, headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString())) }
        val api = TmdbApi("key", engine) // Creamos la API con este simulador.
        val res = api.populares() // Hacemos la llamada.
        
        // Verificamos el resultado:
        assertEquals("Peli 1", res.results[0].title) // Se espera que el título sea: "Peli 1"
    }

    // Prueba 2: Verificar qué sucede si el servidor nos manda una lista vacía de películas.
    @Test
    fun testJsonVacio() = runTest {
        val jsonVacio = """{"page": 1, "results": [], "total_pages": 0}"""
        val engine = MockEngine { respond(jsonVacio, HttpStatusCode.OK, headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString())) }
        val api = TmdbApi("key", engine)
        val res = api.populares()
        
        // Verificamos el resultado:
        assertTrue(res.results.isEmpty()) // Se espera que la lista de resultados esté: vacía (true)
    }

    // Prueba 3: Verificar que el sistema detecta cuando la llave de la API es incorrecta (Error 401).
    @Test
    fun testError401LlaveInvalida() = runTest {
        val engine = MockEngine { respond("Unauthorized", HttpStatusCode.Unauthorized) }
        val api = TmdbApi("key_mala", engine)
        
        // Verificamos el resultado:
        assertFails { api.populares() } // Se espera que la función: falle lanzando una excepción
    }

    // Prueba 4: Verificar cómo reacciona la app cuando hay un problema interno en el servidor (Error 500).
    @Test
    fun testErrorDeRed() = runTest {
        val engine = MockEngine { respondError(HttpStatusCode.InternalServerError) }
        val api = TmdbApi("key", engine)
        
        // Verificamos el resultado:
        assertFails { api.populares() } // Se espera que la función: falle por error de servidor
    }
    
    // Prueba 5: Verificar la llamada a las películas mejor calificadas (topRated).
    @Test
    fun testTopRatedExito() = runTest {
        val engine = MockEngine { respond(jsonExito, HttpStatusCode.OK, headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString())) }
        val api = TmdbApi("key", engine)
        val res = api.topRated()
        
        // Verificamos el resultado:
        assertEquals(1, res.results.size) // Se espera que el tamaño de la lista sea: 1
    }

    // Prueba 6: Verificar la llamada a las películas en cartelera (nowPlaying).
    @Test
    fun testNowPlayingExito() = runTest {
        val engine = MockEngine { respond(jsonExito, HttpStatusCode.OK, headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString())) }
        val api = TmdbApi("key", engine)
        val res = api.nowPlaying()
        
        // Verificamos el resultado:
        assertEquals(1, res.results.size) // Se espera que el tamaño de la lista sea: 1
    }

    // Prueba 7: Verificar que la función de búsqueda funciona correctamente.
    @Test
    fun testBuscarExito() = runTest {
        val engine = MockEngine { respond(jsonExito, HttpStatusCode.OK, headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString())) }
        val api = TmdbApi("key", engine)
        val res = api.buscar("Batman")
        
        // Verificamos el resultado:
        assertEquals(1, res.results.size) // Se espera que se encuentre: 1 resultado
    }

    // Prueba 8: Verificar que podemos obtener los detalles de una película específica por su ID.
    @Test
    fun testDetalleExito() = runTest {
        val jsonDetalle = """{"id": 1, "title": "Batman", "overview": "...", "vote_average": 9.0}"""
        val engine = MockEngine { respond(jsonDetalle, HttpStatusCode.OK, headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString())) }
        val api = TmdbApi("key", engine)
        val res = api.detalle(1)
        
        // Verificamos el resultado:
        assertEquals("Batman", res.title) // Se espera que el título de la peli con ID 1 sea: "Batman"
    }
}
