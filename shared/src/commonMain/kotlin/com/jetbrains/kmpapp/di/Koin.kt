package com.jetbrains.kmpapp.di

import com.jetbrains.kmpapp.data.BookApi
import com.jetbrains.kmpapp.data.BookRepository
import com.jetbrains.kmpapp.screens.BookListViewModel
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.dsl.module

val dataModule =
    module {
        single {
            val json = Json { ignoreUnknownKeys = true }
            HttpClient {
                install(ContentNegotiation) {
                    json(json, contentType = ContentType.Any)
                }
            }
        }
        single { BookApi(get()) }
        single { BookRepository(get()) }
    }

val viewModelModule =
    module {
        factory { BookListViewModel(get()) }
    }

fun initKoin() {
    startKoin {
        modules(
            dataModule,
            viewModelModule,
        )
    }
}
