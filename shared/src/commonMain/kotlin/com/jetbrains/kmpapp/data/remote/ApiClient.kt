package com.jetbrains.kmpapp.data.remote

import com.jetbrains.kmpapp.domain.model.AppError
import com.jetbrains.kmpapp.domain.model.Resultado
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import kotlinx.io.IOException
import kotlinx.serialization.SerializationException
import io.ktor.client.network.sockets.ConnectTimeoutException
import io.ktor.client.network.sockets.SocketTimeoutException

class ApiClient(private val client: HttpClient) {

    suspend fun buscarEnItunes(query: String): Resultado<List<ItemDTO>> {
        return try {
            val response: ITunesResponseDto = client.get("https://itunes.apple.com/search") {
                parameter("term", query)
                parameter("limit", 50)
            }.body()
            
            Resultado.Exito(response.results)
        } catch (e: Exception) {
            // Logueamos el error completo para debuggear problemas silenciosos (como fallos de parseo)
            e.printStackTrace()
            Resultado.Error(mapError(e))
        }
    }

    private fun mapError(e: Exception): AppError {
        return when (e) {
            is HttpRequestTimeoutException,
            is ConnectTimeoutException,
            is SocketTimeoutException -> AppError.Timeout
            
            is ClientRequestException -> AppError.HttpCliente
            is ServerResponseException -> AppError.HttpServidor
            
            is SerializationException -> AppError.Parseo
            
            is IOException -> AppError.SinConexion
            
            else -> AppError.Desconocido
        }
    }
}
