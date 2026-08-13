package com.jetbrains.kmpapp.data.local

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOneOrNull
import com.jetbrains.kmpapp.db.OpenLibraryDatabase
import com.jetbrains.kmpapp.domain.model.Atributo
import com.jetbrains.kmpapp.domain.model.Item
import com.jetbrains.kmpapp.domain.model.ItemDetalle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

@OptIn(ExperimentalCoroutinesApi::class)
class ItemLocalDataSource(database: OpenLibraryDatabase) {
    private val queries = database.openLibraryDatabaseQueries

    fun getItems(): Flow<List<Item>> {
        return queries.selectAllItems().asFlow().mapToList(Dispatchers.IO).flatMapLatest { entities ->
            if (entities.isEmpty()) return@flatMapLatest flowOf(emptyList())
            
            val itemFlows = entities.map { entity ->
                queries.selectTagsForItem(entity.id).asFlow().mapToList(Dispatchers.IO).map { tags ->
                    Item(
                        id = entity.id,
                        titulo = entity.titulo,
                        subtitulo = entity.subtitulo,
                        imagenUrl = entity.imagenUrl,
                        metrica = entity.metrica,
                        fecha = entity.fecha,
                        tags = tags,
                    )
                }
            }
            combine(itemFlows) { it.toList() }
        }
    }

    fun getDetail(id: String): Flow<ItemDetalle?> {
        val itemFlow = queries.selectItemById(id).asFlow().mapToOneOrNull(Dispatchers.IO)
        val detailFlow = queries.selectDetailById(id).asFlow().mapToOneOrNull(Dispatchers.IO)
        val tagsFlow = queries.selectTagsForItem(id).asFlow().mapToList(Dispatchers.IO)
        val attrsFlow = queries.selectAtributosForDetail(id).asFlow().mapToList(Dispatchers.IO)

        return combine(itemFlow, detailFlow, tagsFlow, attrsFlow) { itemEntity, detailEntity, tags, attrs ->
            if (itemEntity == null || detailEntity == null) return@combine null

            val item = Item(
                id = itemEntity.id,
                titulo = itemEntity.titulo,
                subtitulo = itemEntity.subtitulo,
                imagenUrl = itemEntity.imagenUrl,
                metrica = itemEntity.metrica,
                fecha = itemEntity.fecha,
                tags = tags,
            )

            ItemDetalle(
                item = item,
                descripcion = detailEntity.descripcion,
                atributos = attrs.map { Atributo(it.etiqueta, it.valor) },
                relacionados = emptyList(),
            )
        }
    }

    suspend fun upsertItems(items: List<Item>) {
        queries.transaction {
            val now = getCurrentMillis()
            items.forEach { item ->
                queries.insertItem(
                    id = item.id,
                    titulo = item.titulo,
                    subtitulo = item.subtitulo,
                    imagenUrl = item.imagenUrl,
                    metrica = item.metrica,
                    fecha = item.fecha,
                    id_ = item.id,
                    cachedAt = now,
                )
                queries.deleteTags(item.id)
                item.tags.forEach { tag ->
                    queries.insertTag(item.id, tag)
                }
            }
        }
    }

    suspend fun upsertDetail(detail: ItemDetalle) {
        queries.transaction {
            queries.insertDetalle(detail.item.id, detail.descripcion)
            queries.deleteAtributos(detail.item.id)
            detail.atributos.forEach { attr ->
                queries.insertAtributo(detail.item.id, attr.etiqueta, attr.valor)
            }
        }
    }

    suspend fun toggleFavorite(id: String, isFavorite: Boolean) {
        queries.updateFavorite(isFavorite, id)
    }

    suspend fun clearOldCache(threshold: Long) {
        queries.deleteNonFavoriteItemsOlderThan(threshold)
    }
}
