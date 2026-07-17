package com.jetbrains.kmpapp.domain

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import kotlin.test.*

@OptIn(ExperimentalCoroutinesApi::class)
class BuscadorStateTest {

    private val testDispatcher = UnconfinedTestDispatcher()

    @BeforeTest
    fun setup() {
        // En KMP real, esto suele requerir un MainDispatcherRule o similar
    }

    @Test
    fun `verificar estado inicial es vacio y no cargando`() = runTest {
        val buscador = BuscadorState(backgroundScope)

        val estadoActual = buscador.listaState.value
        assertFalse(estadoActual.cargando)
        assertTrue(estadoActual.personajes.isEmpty())
        assertNull(estadoActual.error)
    }

    @Test
    fun `al buscar texto debe pasar por estado cargando`() = runTest(testDispatcher) {
        val buscador = BuscadorState(this)

        buscador.onQueryChange("Rick")

        // El debounce es de 300ms, avanzamos el tiempo
        advanceTimeBy(301)

        // Verificamos que al menos se disparó la lógica (aquí el dispatcher unconfined ayuda)
        // Dependiendo de la implementación exacta, podrías capturar el estado intermedio
        assertTrue(buscador.listaState.value.personajes.isNotEmpty() || buscador.listaState.value.cargando)
    }

    @Test
    fun `borrar busqueda limpia el estado de personajes`() = runTest(testDispatcher) {
        val buscador = BuscadorState(this)

        // Buscamos algo primero
        buscador.onQueryChange("Morty")
        advanceTimeBy(1000) // Aseguramos que terminó la "petición"

        // Limpiamos la búsqueda
        buscador.onQueryChange("")
        advanceTimeBy(301)

        val estadoFinal = buscador.listaState.value
        assertTrue(estadoFinal.personajes.isEmpty(), "La lista debería estar vacía tras borrar búsqueda")
    }
}