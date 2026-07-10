package com.jetbrains.kmpapp.util

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class UtilTest {

    @Test
    fun testNuevoUuid() {
        val uuid = nuevoUuid()
        assertTrue(uuid.isNotEmpty())
    }

    @Test
    fun testEpochMillis() {
        val millis = epochMillis()
        assertTrue(millis > 0)
    }

    @Test
    fun testRelojAhora() {
        val reloj = Reloj()
        assertTrue(reloj.ahora() > 0)
    }

    @Test
    fun testCarritoGenericoStrings() {
        val carrito = Carrito<String>()
        carrito.agregar("Inception")
        assertEquals(1, carrito.items.value.size)
        assertEquals("Inception", carrito.items.value[0])
    }

    @Test
    fun testCarritoGenericoNumeros() {
        val carrito = Carrito<Int>()
        carrito.agregar(42)
        assertEquals(1, carrito.items.value.size)
        assertEquals(42, carrito.items.value[0])
    }

    @Test
    fun testCarritoQuitar() {
        val carrito = Carrito<String>()
        carrito.agregar("A")
        carrito.agregar("B")
        carrito.quitar("A")
        assertEquals(1, carrito.items.value.size)
        assertEquals("B", carrito.items.value[0])
    }

    @Test
    fun testCarritoTotalItems() = runTest {
        val carrito = Carrito<String>()
        carrito.agregar("Peli 1")
        carrito.agregar("Peli 2")
        val total = carrito.totalItems.first()
        assertEquals(2, total)
    }

    @Test
    fun testSearchFilterGenerico() = runTest {
        // Probamos que el filtro funcione con Ints también
        val flow = flowOf(1, 1, 2, 2, 3).searchFilter()
        val result = flow.first()
        assertTrue(result > 0)
    }
}
