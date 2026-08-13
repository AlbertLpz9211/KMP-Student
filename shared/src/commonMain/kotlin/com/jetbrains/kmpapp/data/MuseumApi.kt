package com.jetbrains.kmpapp.data

import io.ktor.client.HttpClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

interface MuseumApi {
    suspend fun getObjects(): List<MuseumObject>
}

class KtorMuseumApi(private val client: HttpClient) : MuseumApi {
    override suspend fun getObjects(): List<MuseumObject> = emptyList()
}
