package com.jetbrains.kmpapp.util

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged

class Buscador {
    // Aplica un retraso para no saturar con cada letra tecleada
    // y evita emitir el mismo texto dos veces seguidas.
    fun procesarBusquedas(entradas: Flow<String>): Flow<String> {
        return entradas
            .debounce(300)
            .distinctUntilChanged()
    }
}