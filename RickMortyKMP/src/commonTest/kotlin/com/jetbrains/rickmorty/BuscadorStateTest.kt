package com.jetbrains.rickmorty

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runCurrent
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class BuscadorStateTest {

    @Test
    fun testInitialState() {
        val buscador = BuscadorState()
        val state = buscador.state.value
        assertFalse(state.cargando)
        assertTrue(state.personajes.isEmpty())
        assertEquals(null, state.error)
    }

    @Test
    fun testDebounceAndDistinct() = runTest {
        val buscador = BuscadorState()
        val queries = MutableSharedFlow<String>(extraBufferCapacity = 10)
        val results = mutableListOf<String>()
        
        val job = launch {
            buscador.filtrarBusqueda(queries).collect {
                results.add(it)
            }
        }
        runCurrent()

        queries.emit("R")
        queries.emit("Ri")
        queries.emit("Rick")
        
        advanceTimeBy(400)
        runCurrent()
        assertEquals(1, results.size)
        assertEquals("Rick", results.last())

        queries.emit("Rick") 
        advanceTimeBy(400)
        runCurrent()
        assertEquals(1, results.size)

        queries.emit("Morty")
        advanceTimeBy(400)
        runCurrent()
        assertEquals(2, results.size)
        assertEquals("Morty", results.last())

        job.cancel()
    }

    @Test
    fun testStateTransitions() {
        val buscador = BuscadorState()
        
        buscador.setCargando()
        assertTrue(buscador.state.value.cargando)
        assertEquals(null, buscador.state.value.error)

        val personajes = listOf("Rick", "Morty")
        buscador.setPersonajes(personajes)
        assertFalse(buscador.state.value.cargando)
        assertEquals(personajes, buscador.state.value.personajes)

        buscador.setError("Error de red")
        assertFalse(buscador.state.value.cargando)
        assertEquals("Error de red", buscador.state.value.error)
    }
}
