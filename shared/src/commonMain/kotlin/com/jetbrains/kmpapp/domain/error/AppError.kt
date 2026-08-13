package com.jetbrains.kmpapp.domain.error

sealed interface AppError {
    data object SinConexion : AppError

    data object Timeout : AppError

    data class HttpClient(val codigo: Int) : AppError

    data class HttpServidor(val codigo: Int) : AppError

    data class Parseo(val detalle: String) : AppError

    data class Desconocido(val detalle: String) : AppError
}
