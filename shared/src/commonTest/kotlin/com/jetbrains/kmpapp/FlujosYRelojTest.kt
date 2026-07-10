package com.jetbrains.kmpapp

import com.jetbrains.kmpapp.util.Carrito
import com.jetbrains.kmpapp.util.Reloj
import com.jetbrains.kmpapp.util.epochMillis
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue


class FlujosYRelojTest {

    @Test
    fun prueba_1_epochMillis_devuelve_valor_positivo() {
        val tiempo = epochMillis()
        assertTrue(tiempo > 0, "El tiempo epochMillis debe ser mayor a 0")
    }

    @Test
    fun prueba_2_reloj_ahora_devuelve_valor_positivo() {
        val reloj = Reloj()
        assertTrue(reloj.ahora() > 0, "El método ahora() del Reloj debe ser mayor a 0")
    }

    @Test
    fun prueba_3_carrito_inicia_vacio() {
        val carrito = Carrito()
        assertEquals(0, carrito.items.value.size, "El carrito debería empezar con 0 ítems")
    }

    @Test
    fun prueba_4_carrito_agrega_item() {
        val carrito = Carrito()
        carrito.agregar("Manzana")
        carrito.agregar("Pera")

        assertEquals(2, carrito.items.value.size)
        assertEquals("Manzana", carrito.items.value[0])
    }

    @Test
    fun prueba_5_carrito_quita_item() {
        val carrito = Carrito()
        carrito.agregar("Monitor")
        carrito.agregar("Teclado")
        carrito.quitar("Monitor")

        assertEquals(1, carrito.items.value.size)
        assertEquals("Teclado", carrito.items.value.first())
    }

    @Test
    fun prueba_6_carrito_total_items_flujo_derivado() = runTest {
        val carrito = Carrito()
        carrito.agregar("Mouse")
        carrito.agregar("Cable USB")
        carrito.agregar("Audifonos")

        // Colectamos el primer valor emitido por el mapa
        val total = carrito.totalItems.first()

        assertEquals(3, total, "El flujo derivado totalItems debe ser igual a 3")
    }
}