package com.jetbrains.kmpapp.domain.model

sealed interface AppError {
    data object SinConexion : AppError
    data object Timeout : AppError
    data object HttpCliente : AppError
    data object HttpServidor : AppError
    data object Parseo : AppError
    data object Desconocido : AppError
}

sealed interface Resultado<out T> {
    data class Exito<T>(val datos: T) : Resultado<T>
    data class Error(val error: AppError) : Resultado<Nothing>
}
