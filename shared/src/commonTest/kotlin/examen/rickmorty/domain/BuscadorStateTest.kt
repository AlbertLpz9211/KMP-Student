package examen.rickmorty.domain

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class BuscadorStateTest {

    private lateinit var buscadorState: BuscadorState

    @BeforeTest
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher())
        buscadorState = BuscadorState()
    }

    @Test
    fun `test initial state is correct`() = runTest {
        val state = buscadorState.state.value
        assertFalse(state.cargando)
        assertTrue(state.personajes.isEmpty())
        assertNull(state.error)
    }

    @Test
    fun `test updateState changes state correctly`() = runTest {
        val newState = ListaState(cargando = true, error = "Error")
        buscadorState.updateState(newState)
        
        val state = buscadorState.state.value
        assertTrue(state.cargando)
        assertEquals("Error", state.error)
    }

    @Test
    fun `test searchQueryFlow applies debounce and distinctUntilChanged`() = runTest {
        val results = mutableListOf<String>()
        val job = launch {
            buscadorState.searchQueryFlow.collect { results.add(it) }
        }

        buscadorState.updateQuery("Ric")
        advanceTimeBy(100)
        buscadorState.updateQuery("Rick")
        advanceTimeBy(100)
        buscadorState.updateQuery("Rick S")
        
        // Initial empty string + "Rick S" after 300ms
        advanceTimeBy(301)
        
        assertEquals(2, results.size)
        assertEquals("", results[0])
        assertEquals("Rick S", results[1])

        job.cancel()
    }
}