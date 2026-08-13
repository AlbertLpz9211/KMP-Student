package com.jetbrains.kmpapp.data.mapper

import com.jetbrains.kmpapp.data.remote.ItemDTO
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class ApiMapperTest {
    private val mapper = ApiMapper()

    @Test
    fun `mapear DTO completo a Item de dominio correctamente`() {
        val dto = ItemDTO(
            trackId = 123L,
            trackName = "Canción Test",
            artistName = "Artista Test",
            artworkUrl100 = "https://test.com/img.jpg",
            trackPrice = 0.99,
            primaryGenreName = "Rock",
            kind = "song",
            wrapperType = "track"
        )

        val item = mapper.toDomain(dto)

        assertEquals("123", item.id)
        assertEquals("Canción Test", item.titulo)
        assertEquals("Artista Test", item.subtitulo)
        assertEquals("https://test.com/img.jpg", item.imagenUrl)
        assertEquals(0.99, item.metrica)
        assertEquals(listOf("Rock", "song", "track"), item.tags)
    }

    @Test
    fun `mapear DTO con campos nulos usa valores por defecto`() {
        val dto = ItemDTO(
            trackId = null,
            collectionId = 456L,
            trackName = null,
            collectionName = "Coleccion Test"
        )

        val item = mapper.toDomain(dto)

        assertEquals("456", item.id)
        assertEquals("Coleccion Test", item.titulo)
        assertNull(item.subtitulo)
        assertEquals(emptyList(), item.tags)
    }

    @Test
    fun `mapear DTO a ItemDetalle incluye campos extendidos`() {
        val dto = ItemDTO(
            trackId = 789L,
            trackName = "Detalle Test",
            longDescription = "Una descripcion larga",
            currency = "USD",
            previewUrl = "https://test.com/preview"
        )

        val detail = mapper.toDetailDomain(dto)

        assertEquals("789", detail.id)
        assertEquals("Una descripcion larga", detail.descripcion)
        assertEquals("USD", detail.moneda)
        assertEquals("https://test.com/preview", detail.urlPreview)
    }
}
