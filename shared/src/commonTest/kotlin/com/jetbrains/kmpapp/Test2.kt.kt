package com.jetbrains.kmpapp

import com.jetbrains.kmpapp.util.Reloj
import com.jetbrains.kmpapp.util.Carrito
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class Test2 {

    @Test
    fun testRelojAhoraDevuelveTiempoValido() {
        val reloj = Reloj()
        assertTrue(reloj.ahora() > 0)
    }

    @Test
    fun testCarritoIniciaVacio() {
        val carrito = Carrito()
        assertEquals(0, carrito.items.value.size)
    }

    @Test
    fun testCarritoAgregarUnProducto() {
        val carrito = Carrito()
        carrito.agregar("Laptop")
        assertEquals(1, carrito.items.value.size)
        assertEquals("Laptop", carrito.items.value[0])
    }

    @Test
    fun testCarritoAgregarMultiplesProductos() {
        val carrito = Carrito()
        carrito.agregar("Laptop")
        carrito.agregar("Mouse")
        assertEquals(2, carrito.items.value.size)
    }

    @Test
    fun testCarritoQuitarProductoExistente() {
        val carrito = Carrito()
        carrito.agregar("Laptop")
        carrito.quitar("Laptop")
        assertEquals(0, carrito.items.value.size)
    }

    @Test
    fun testCarritoQuitarProductoInexistente() {
        val carrito = Carrito()
        carrito.agregar("Laptop")
        carrito.quitar("Mouse")
        assertEquals(1, carrito.items.value.size)
    }
}

