package com.jetbrains.kmpapp.data.remote.mapper

import com.jetbrains.kmpapp.data.remote.dto.BookDto
import com.jetbrains.kmpapp.data.remote.dto.WorkDetailDto
import com.jetbrains.kmpapp.domain.model.Item
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ApiMapperTest {

    @Test
    fun `BookDto toDomain maps correctly`() {
        val dto = BookDto(
            key = "/works/OL123W",
            title = "Title",
            subtitle = "Sub",
            author_name = listOf("Author"),
            first_publish_year = 2024,
            cover_i = 456,
            ratings_average = 4.8,
            subject = listOf("Subject")
        )

        val item = dto.toDomain()

        assertEquals("OL123W", item.id)
        assertEquals("Title", item.titulo)
        assertEquals("Sub", item.subtitulo)
        assertEquals("https://covers.openlibrary.org/b/id/456-M.jpg", item.imagenUrl)
        assertEquals(2024.0, item.fecha?.toDouble())
        assertEquals(4.8, item.metrica)
        assertTrue(item.tags.contains("Author"))
        assertTrue(item.tags.contains("Subject"))
    }

    @Test
    fun `BookDto toDomain with null fields maps correctly`() {
        val dto = BookDto(
            key = "OL123W",
            title = "Title"
        )

        val item = dto.toDomain()

        assertEquals("OL123W", item.id)
        assertEquals(null, item.imagenUrl)
        assertTrue(item.tags.isEmpty())
    }

    @Test
    fun `WorkDetailDto toDomain with string description maps correctly`() {
        val baseItem = Item("ID", "T", null, null, null, null, emptyList())
        val dto = WorkDetailDto(
            key = "ID",
            title = "T",
            description = JsonPrimitive("Description content")
        )

        val detail = dto.toDomain(baseItem)

        assertEquals("Description content", detail.descripcion)
    }

    @Test
    fun `WorkDetailDto toDomain with object description maps correctly`() {
        val baseItem = Item("ID", "T", null, null, null, null, emptyList())
        val dto = WorkDetailDto(
            key = "ID",
            title = "T",
            description = buildJsonObject { put("value", "Object content") }
        )

        val detail = dto.toDomain(baseItem)

        assertEquals("Object content", detail.descripcion)
    }
}
