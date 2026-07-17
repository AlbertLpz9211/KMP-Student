package com.jetbrains.kmpapp.di

import com.jetbrains.kmpapp.data.* // Asegúrate de importar RickMortyApi y RickMortyRepository
import com.jetbrains.kmpapp.screens.detail.DetailViewModel
import com.jetbrains.kmpapp.screens.list.ListViewModel
import com.jetbrains.kmpapp.screens.list.CharactersViewModel // ViewModel para la sesión de Rick & Morty
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val dataModule = module {
    single {
        val json = Json {
            ignoreUnknownKeys = true
            // Añadimos esto por seguridad para las respuestas de Rick & Morty
            coerceInputValues = true
        }
        HttpClient {
            install(ContentNegotiation) {
                json(json, contentType = ContentType.Any)
            }
        }
        val dataModule = module {
            // ... otros registros ...

            // Registro de la Base de Datos
            single {
                val driver = get<DriverFactory>().createDriver()
                AppDatabase(driver)
            }

            // Registro del DataSource
            single { CharacterLocalDataSource(get()) }
        }
    }

    // --- Módulo Museum (Existente) ---
    single<MuseumApi> { KtorMuseumApi(get()) }
    single<MuseumStorage> { InMemoryMuseumStorage() }
    single {
        MuseumRepository(get(), get()).apply {
            initialize()
        }
    }

    // --- NUEVO: Módulo Rick & Morty (Sesión 3) ---
    single { RickMortyApi(get()) } // Usa el HttpClient inyectado arriba
    single { RickMortyRepository(get()) }
}

val viewModelModule = module {
    // ViewModels originales
    factoryOf(::ListViewModel)
    factoryOf(::DetailViewModel)

    // --- NUEVO: ViewModel para Personajes (Sesión 2 y 3) ---
    // Usamos factory para que se cree una instancia nueva cada vez que entramos a la pantalla
    factory { CharactersViewModel(get()) }
}

fun initKoin() {
    startKoin {
        modules(
            dataModule,
            viewModelModule,
        )
    }
}