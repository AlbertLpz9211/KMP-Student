package com.jetbrains.kmpapp.data.remote

import com.jetbrains.kmpapp.data.remote.dto.SearchResponseDto
import com.jetbrains.kmpapp.data.remote.dto.WorkDetailDto
import com.jetbrains.kmpapp.domain.error.AppError
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.io.IOException
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json

class OpenLibraryApi(private val client: HttpClient) {

    companion object {
        fun createDefaultClient(): HttpClient {
            return HttpClient {
                install(ContentNegotiation) {
                    json(Json {
                        ignoreUnknownKeys = true
                        isLenient = true
                    })
                }
            }
        }
    }

    suspend fun searchBooks(query: String): Result<SearchResponseDto> {
        return safeApiCall {
            client.get("https://openlibrary.org/search.json") {
                parameter("q", query)
                contentType(ContentType.Application.Json)
            }.body()
        }
    }

    suspend fun getWorkDetail(workId: String): Result<WorkDetailDto> {
        val cleanId = workId.removePrefix("/works/").removePrefix("works/")
        return safeApiCall {
            client.get("https://openlibrary.org/works/$cleanId.json") {
                contentType(ContentType.Application.Json)
            }.body()
        }
    }

    private suspend fun <T> safeApiCall(block: suspend () -> T): Result<T> {
        return try {
            Result.success(block())
        } catch (e: HttpRequestTimeoutException) {
            Result.failure(AppErrorException(AppError.Timeout))
        } catch (e: ClientRequestException) {
            Result.failure(AppErrorException(AppError.HttpClient(e.response.status.value)))
        } catch (e: ServerResponseException) {
            Result.failure(AppErrorException(AppError.HttpServidor(e.response.status.value)))
        } catch (e: SerializationException) {
            Result.failure(AppErrorException(AppError.Parseo(e.message ?: "Error de parseo")))
        } catch (e: IOException) {
            Result.failure(AppErrorException(AppError.SinConexion))
        } catch (e: Exception) {
            Result.failure(AppErrorException(AppError.Desconocido(e.message ?: "Error desconocido")))
        }
    }
}

class AppErrorException(val error: AppError) : Exception(error.toString())
