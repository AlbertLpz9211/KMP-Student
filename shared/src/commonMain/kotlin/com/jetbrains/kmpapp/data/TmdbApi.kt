package com.jetbrains.kmpapp.data

import com.jetbrains.kmpapp.data.dto.MovieDto
import com.jetbrains.kmpapp.data.dto.MoviePageDto
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

// Definimos la clase TmdbApi que se encargará de hacer las peticiones a la API de películas.
// Recibe una llave (apiKey) y opcionalmente un motor de cliente (engine) para pruebas.
class TmdbApi(private val apiKey: String, engine: HttpClientEngine? = null) {
    
    // Creamos el cliente de HttpClient. Si se proporciona un motor de prueba, lo usamos;
    // de lo contrario, creamos uno por defecto con nuestra configuración personalizada.
    private val client = if (engine != null) HttpClient(engine) { config(this) } else HttpClient { config(this) }

    // Función privada para centralizar la configuración del cliente HttpClient.
    // Aquí definimos cómo se deben manejar los datos y las peticiones base.
    private fun config(config: io.ktor.client.HttpClientConfig<*>) {
        config.apply {
            // Instalamos la negociación de contenido para que el cliente sepa cómo 
            // convertir automáticamente los archivos JSON que vienen de internet a objetos Kotlin.
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true // Si la API manda datos que no definimos, los ignora en vez de fallar.
                    coerceInputValues = true // Ayuda a manejar datos nulos o inesperados de forma más segura.
                })
            }
            
            // Configuramos una petición por defecto que se aplicará a todas las llamadas.
            // Esto evita tener que escribir la URL base y la llave en cada función.
            defaultRequest {
                url("https://api.themoviedb.org/3/") // Dirección principal del servidor de películas.
                url.parameters.append("api_key", apiKey) // Agregamos tu llave de acceso a la URL.
                url.parameters.append("language", "es-MX") // Pedimos que la información venga en español.
            }
        }
    }

    // Función para obtener la lista de películas populares.
    // Es una función 'suspend' porque se ejecuta de forma asíncrona mientras espera la respuesta de internet.
    suspend fun populares(pagina: Int = 1): MoviePageDto = 
        client.get("movie/popular") {
            parameter("page", pagina) // Le indicamos al servidor qué número de página queremos ver.
        }.body() // El '.body()' convierte el resultado JSON al objeto MoviePageDto.

    // Función para obtener las películas mejor calificadas por los usuarios.
    suspend fun topRated(pagina: Int = 1): MoviePageDto = 
        client.get("movie/top_rated") {
            parameter("page", pagina)
        }.body()

    // Función para obtener las películas que están actualmente en cartelera en los cines.
    suspend fun nowPlaying(pagina: Int = 1): MoviePageDto = 
        client.get("movie/now_playing") {
            parameter("page", pagina)
        }.body()

    // Función para buscar películas a través de un texto o consulta (query) específica.
    suspend fun buscar(query: String, pagina: Int = 1): MoviePageDto = 
        client.get("search/movie") {
            parameter("query", query) // El texto que el usuario escribió para buscar.
            parameter("page", pagina)
        }.body()

    // Función para obtener la información detallada de una sola película usando su identificador único (ID).
    suspend fun detalle(movieId: Int): MovieDto = 
        client.get("movie/$movieId").body()
}
