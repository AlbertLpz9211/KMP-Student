package com.jetbrains.kmpapp.screens.list

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse

@OptIn(ExperimentalCoroutinesApi::class)
class BuscadorStateTest {

    @Test
    fun testInitialState() = runTest {
        val buscadorState = BuscadorState()
        val state = buscadorState.state.value
        val query = buscadorState.query.value

        assertFalse(state.cargando)
        assertEquals(emptyList(), state.personajes)
        assertEquals(null, state.error)
        assertEquals("", query)
    }

    @Test
    fun testQueryChange() = runTest {
        val buscadorState = BuscadorState()
        buscadorState.onQueryChange("Rick")
        
        assertEquals("Rick", buscadorState.query.value)
    }

    @Test
    fun testDebouncedQueryEmitsCorrectValue() = runTest {
        val buscadorState = BuscadorState()
        val results = mutableListOf<String>()
        
        // Start collecting debouncedQuery
        val job = launch {
            buscadorState.debouncedQuery.collect { results.add(it) }
        }

        buscadorState.onQueryChange("R")
        advanceTimeBy(100)
        buscadorState.onQueryChange("Ri")
        advanceTimeBy(100)
        buscadorState.onQueryChange("Rick")
        
        advanceTimeBy(301)
        
        assertEquals("Rick", results.last())
        job.cancel()
    }
}
