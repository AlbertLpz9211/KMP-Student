package com.jetbrains.kmpapp.data

import com.jetbrains.kmpapp.domain.model.Atributo
import com.jetbrains.kmpapp.domain.model.Item
import com.jetbrains.kmpapp.domain.model.ItemDetalle

class BookRepository(private val api: BookApi) {
    suspend fun getBooks(): List<Item> {
        return api.getBooks().docs?.map { it.toDomain() } ?: emptyList()
    }

    suspend fun getBookDetails(id: String): ItemDetalle {
        val dto = api.getBookDetails(id)
        return ItemDetalle(
            item = dto.toDomain(),
            descripcion = "Detalles para ${dto.title}",
            atributos =
                listOf(
                    Atributo("Publicado", dto.firstPublishYear?.toString() ?: "N/A"),
                    Atributo("Materias", dto.subject?.take(5)?.joinToString() ?: "N/A"),
                ),
            relacionados = emptyList(),
        )
    }

    private fun BookDocDto.toDomain(): Item =
        Item(
            id = id,
            titulo = title,
            subtitulo = authors?.joinToString(),
            imagenUrl = coverId?.let { "https://covers.openlibrary.org/b/id/$it-M.jpg" },
            metrica = null,
            fecha = firstPublishYear?.toString(),
            tags = subject ?: emptyList(),
        )
}
