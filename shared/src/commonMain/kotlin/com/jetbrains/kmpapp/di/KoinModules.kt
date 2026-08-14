package com.jetbrains.kmpapp.di

import com.jetbrains.kmpapp.data.local.DriverFactory
import com.jetbrains.kmpapp.data.local.ItemLocalDataSource
import com.jetbrains.kmpapp.data.local.MyDatabase
import com.jetbrains.kmpapp.data.local.SqlDelightItemLocalDataSource
import com.jetbrains.kmpapp.data.mapper.ApiMapper
import com.jetbrains.kmpapp.data.remote.ApiClient
import com.jetbrains.kmpapp.data.repository.ItemRepositoryImpl
import com.jetbrains.kmpapp.domain.repository.ItemRepository
import com.jetbrains.kmpapp.presentation.DetalleViewModel
import com.jetbrains.kmpapp.presentation.ListaViewModel
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

val commonModule = module {
    single {
        HttpClient {
            install(ContentNegotiation) {
                val json = Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                }
                json(json)
                json(json, contentType = ContentType.Text.JavaScript)
            }
        }
    }

    singleOf(::ApiClient)
    singleOf(::ApiMapper)
    
    single {
        val driver = get<DriverFactory>().createDriver()
        MyDatabase(driver)
    }

    single<ItemLocalDataSource> { SqlDelightItemLocalDataSource(get()) }
    
    single<ItemRepository> { 
        ItemRepositoryImpl(get(), get(), get(), { 
            // En un proyecto real inyectaríamos un Clock
            0L 
        }) 
    }

    factoryOf(::ListaViewModel)
    factoryOf(::DetalleViewModel)
}

expect val platformModule: Module

fun initKoin(appDeclaration: KoinAppDeclaration = {}) = startKoin {
    appDeclaration()
    modules(commonModule, platformModule)
}

// Helper para iOS
fun initKoin() = initKoin {}
