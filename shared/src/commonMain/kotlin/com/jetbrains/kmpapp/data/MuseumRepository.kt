package com.jetbrains.kmpapp.data

import kotlinx.coroutines.flow.Flow

class MuseumRepository(
    private val api: MuseumApi,
    private val storage: MuseumStorage
) {
    fun getObjects(): Flow<List<MuseumObject>> = storage.getObjects()
    fun getObjectById(id: Int): Flow<MuseumObject?> = storage.getObjectById(id)
    suspend fun initialize() {}
}
