package com.jetbrains.kmpapp.data

import app.cash.sqldelight.adapter.primitive.IntColumnAdapter
import app.cash.sqldelight.sqlite.driver.JdbcSqliteDriver
import com.jetbrains.kmpapp.AppDatabase
import com.jetbrains.kmpapp.data.dto.CharacterDto
import com.jetbrains.kmpapp.data.dto.CharacterResponse
import com.jetbrains.kmpapp.data.dto.InfoDto
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class PersonajeRepositoryTest {

    @Test
    fun `verificar que refrescar guarda datos y observar los emite`() = runTest {
        // Preparación: DB en memoria
        val driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
        AppDatabase.Schema.create(driver)
        val database = AppDatabase(driver)
        val localDataSource = CharacterLocalDataSource(database)

        // Mock simple de la API (FakeApi)
        val fakeApi = object : RickMortyApi(HttpClient {}) { // El HttpClient no se usará
            override suspend fun getCharacters(page: Int): CharacterResponse {
                return CharacterResponse(
                    info = InfoDto(1, 1, null, null),
                    results = listOf(
                        CharacterDto(1, "Rick", "Alive", "Human", "Male", "url")
                    )
                )
            }
        }

        val repository = PersonajeRepository(fakeApi, localDataSource)

        // 1. Verificar estado inicial vacío
        val inicial = repository.observar().first()
        assertTrue(inicial.isEmpty())

        // 2. (b) Ejecutar refrescar
        repository.refrescar()

        // 3. (c) Verificar que observar() ahora emite la lista guardada
        val resultado = repository.observar().first()
        assertEquals(1, resultado.size)
        assertEquals("Rick", resultado[0].name)
    }
}