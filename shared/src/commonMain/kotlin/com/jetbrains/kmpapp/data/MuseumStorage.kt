package com.jetbrains.kmpapp.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

interface MuseumStorage {
    fun getObjects(): Flow<List<MuseumObject>>
    suspend fun saveObjects(objects: List<MuseumObject>)
    fun getObjectById(id: Int): Flow<MuseumObject?>
}

class InMemoryMuseumStorage : MuseumStorage {
    override fun getObjects(): Flow<List<MuseumObject>> = flowOf(emptyList())
    override suspend fun saveObjects(objects: List<MuseumObject>) {}
    override fun getObjectById(id: Int): Flow<MuseumObject?> = flowOf(null)
}
