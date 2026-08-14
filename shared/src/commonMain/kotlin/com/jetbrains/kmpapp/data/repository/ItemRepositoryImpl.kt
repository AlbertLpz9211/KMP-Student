package com.jetbrains.kmpapp.data.repository

import com.jetbrains.kmpapp.data.local.ItemLocalDataSource
import com.jetbrains.kmpapp.data.mapper.ApiMapper
import com.jetbrains.kmpapp.data.remote.ApiClient
import com.jetbrains.kmpapp.domain.model.Item
import com.jetbrains.kmpapp.domain.model.Resultado
import com.jetbrains.kmpapp.domain.repository.ItemRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class ItemRepositoryImpl(
    private val apiClient: ApiClient,
    private val localDataSource: ItemLocalDataSource,
    private val mapper: ApiMapper,
    private val clock: () -> Long // Proveedor de tiempo actual en milisegundos
) : ItemRepository {

    companion object {
        private const val CACHE_TTL_MILLIS = 5 * 60 * 1000L // 5 minutos
    }

    override fun observarCatalogo(): Flow<List<Item>> {
        return localDataSource.getAllItems()
    }

    override suspend fun buscar(query: String): Resultado<Unit> = withContext(Dispatchers.IO) {
        val ultimaActualizacion = localDataSource.getUltimaActualizacion(query)
        
        if (debeActualizar(ultimaActualizacion)) {
            fetchFromNetwork(query)
        } else {
            Resultado.Exito(Unit)
        }
    }

    override suspend fun alternarFavorito(id: String) = withContext(Dispatchers.IO) {
        val esFavorito = localDataSource.isFavorito(id)
        localDataSource.setFavorito(id, !esFavorito)
    }

    private fun debeActualizar(ultimaActualizacion: Long?): Boolean {
        if (ultimaActualizacion == null) return true
        val ahora = clock()
        return (ahora - ultimaActualizacion) > CACHE_TTL_MILLIS
    }

    private suspend fun fetchFromNetwork(query: String): Resultado<Unit> {
        return when (val response = apiClient.buscarEnItunes(query)) {
            is Resultado.Exito -> {
                val items = response.datos.map { mapper.toDomain(it) }
                localDataSource.insertItems(items)
                localDataSource.marcarActualizacion(query, clock())
                Resultado.Exito(Unit)
            }
            is Resultado.Error -> {
                Resultado.Error(response.error)
            }
        }
    }
}
