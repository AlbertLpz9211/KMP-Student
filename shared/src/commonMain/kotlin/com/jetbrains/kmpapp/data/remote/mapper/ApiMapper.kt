package com.jetbrains.kmpapp.data.remote.mapper

import com.jetbrains.kmpapp.data.remote.dto.BookDto
import com.jetbrains.kmpapp.data.remote.dto.WorkDetailDto
import com.jetbrains.kmpapp.domain.model.Atributo
import com.jetbrains.kmpapp.domain.model.Item
import com.jetbrains.kmpapp.domain.model.ItemDetalle
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.jsonPrimitive

fun BookDto.toDomain(): Item {
    val cleanId = key.removePrefix("/works/").removePrefix("works/")
    val imageUrl = cover_i?.let { "https://covers.openlibrary.org/b/id/$it-M.jpg" }
    
    val allTags = mutableListOf<String>()
    allTags.addAll(author_name)
    allTags.addAll(subject)

    return Item(
        id = cleanId,
        titulo = title,
        subtitulo = subtitle,
        imagenUrl = imageUrl,
        metrica = ratings_average,
        fecha = first_publish_year?.toString(),
        tags = allTags
    )
}

fun WorkDetailDto.toDomain(baseItem: Item): ItemDetalle {
    val desc = when (description) {
        is JsonPrimitive -> description.content
        is JsonObject -> {
            description["value"]?.jsonPrimitive?.content ?: ""
        }
        else -> ""
    }

    val attrs = mutableListOf<Atributo>()
    if (first_publish_date != null) {
        attrs.add(Atributo("Fecha de publicación", first_publish_date))
    }
    subjects.forEach { 
        attrs.add(Atributo("Tema", it))
    }

    return ItemDetalle(
        item = baseItem,
        descripcion = desc,
        atributos = attrs,
        relacionados = emptyList()
    )
}
