package com.jetbrains.kmpapp.data.local

import com.jetbrains.kmpapp.db.OpenLibraryDatabase
import com.jetbrains.kmpapp.domain.model.Atributo
import com.jetbrains.kmpapp.domain.model.Item
import com.jetbrains.kmpapp.domain.model.ItemDetalle
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class ItemLocalDataSourceTest {
    private lateinit var database: OpenLibraryDatabase
    private lateinit var dataSource: ItemLocalDataSource

    @BeforeTest
    fun setup() {
        val driver = createTestDriver()
        // Try without explicit adapter first, maybe SQLDelight 2.x handles kotlin.Boolean
        database = OpenLibraryDatabase(driver)
        dataSource = ItemLocalDataSource(database)
    }

    @Test
    fun `upsertItems and getItems returns correct list`() = runTest {
        val item = Item("1", "T", "S", "U", 4.5, "2024", listOf("tag1"))
        dataSource.upsertItems(listOf(item))

        val result = dataSource.getItems().first()

        assertEquals(1, result.size)
        assertEquals(item.id, result.first().id)
        assertEquals(item.titulo, result.first().titulo)
    }

    @Test
    fun `upsertDetail and getDetail returns correct data`() = runTest {
        val item = Item("1", "T", null, null, null, null, emptyList())
        val detail = ItemDetalle(item, "Desc", listOf(Atributo("E", "V")), emptyList())
        
        dataSource.upsertItems(listOf(item))
        dataSource.upsertDetail(detail)

        val result = dataSource.getDetail("1").first()

        assertEquals("Desc", result?.descripcion)
        assertEquals(1, result?.atributos?.size)
        assertEquals("E", result?.atributos?.first()?.etiqueta)
    }
}
