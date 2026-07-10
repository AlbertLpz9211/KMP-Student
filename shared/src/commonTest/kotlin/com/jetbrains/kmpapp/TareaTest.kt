package com.jetbrains.kmpapp

import com.jetbrains.kmpapp.util.Buscador
import com.jetbrains.kmpapp.util.Carrito
import com.jetbrains.kmpapp.util.Reloj
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest

class TareaTest {

    // Test 1: Comprobar el tiempo
    @Test
    fun test_reloj_devuelve_tiempo_valido() {
        val reloj = Reloj()
        assertTrue(reloj.ahora() > 0, "El tiempo en milisegundos debe ser mayor a 0")
    }

    // Test 2: Estado inicial del carrito
    @Test
    fun test_carrito_inicia_vacio() {
        val carrito = Carrito()
        assertEquals(0, carrito.items.value.size)
    }

    // Test 3: Agregar al carrito
    @Test
    fun test_carrito_agrega_item() {
        val carrito = Carrito()
        carrito.agregar("Audífonos")
        assertEquals(1, carrito.items.value.size)
        assertEquals("Audífonos", carrito.items.value[0])
    }

    // Test 4: Quitar del carrito
    @Test
    fun test_carrito_quita_item() {
        val carrito = Carrito()
        carrito.agregar("Teclado")
        carrito.agregar("Ratón")
        carrito.quitar("Teclado")

        assertEquals(1, carrito.items.value.size)
        assertEquals("Ratón", carrito.items.value[0])
    }

    // Test 5: Flujo derivado (totalItems)
    @Test
    fun test_carrito_flujo_derivado_total_items() = runTest {
        val carrito = Carrito()
        carrito.agregar("Monitor")
        carrito.agregar("Cable")

        // Recolectamos el primer valor que emite el flujo derivado
        val total = carrito.totalItems.first()
        assertEquals(2, total)
    }

    // Test 6: Operadores del buscador
    @Test
    fun test_buscador_evita_duplicados_consecutivos() = runTest {
        val buscador = Buscador()
        // flowOf emite estos valores automáticamente
        val entradas = flowOf("k", "ko", "kot", "kot")


        val resultados = buscador.procesarBusquedas(entradas).toList()


        assertEquals(3, resultados.size)
        assertEquals("kot", resultados.last())
    }
}

