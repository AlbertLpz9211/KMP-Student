package com.jetbrains.kmpapp.util

import kotlinx.coroutines.flow.*

@OptIn(kotlinx.coroutines.FlowPreview::class)
fun filtrarBusqueda(input: Flow<String>): Flow<String> {
    return input.debounce(300).distinctUntilChanged()
}

