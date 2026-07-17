package com.jetbrains.kmpapp.rickmorty

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

class BuscadorStateTest {

    @Test
    fun `estado inicial es correcto`() {
        val buscador = BuscadorState()
        val state = buscador.state.value
        assertFalse(state.cargando)
        assertTrue(state.personajes.isEmpty())
        assertNull(state.error)
    }

    @Test
    fun `transicion a cargando`() {
        val buscador = BuscadorState()
        buscador.setCargando(true)
        assertTrue(buscador.state.value.cargando)
    }

    @Test
    fun `transicion a personajes cargados`() {
        val buscador = BuscadorState()
        val personajes = listOf("Rick", "Morty")
        buscador.setPersonajes(personajes)
        
        val state = buscador.state.value
        assertFalse(state.cargando)
        assertEquals(personajes, state.personajes)
        assertNull(state.error)
    }
}
