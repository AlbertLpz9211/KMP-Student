package com.jetbrains.kmpapp

sealed interface Resultado {
    data class Ok(val valor: String) : Resultado
    data class Fallo(val mensaje: String) : Resultado
}

fun procesar(r: Resultado) {
    when (r) {
        is Resultado.Ok -> println("Éxito: ${r.valor}")
        is Resultado.Fallo -> println("Error: ${r.mensaje}")
    }
}

fun String.esLarga(): Boolean = this.length > 10
fun Int.duplicar(): Int = this * 2
fun List<String>.primero(): String? = this.firstOrNull()
fun Double.impuesto(): Double = this * 1.16
fun Boolean.invertir(): Boolean = !this

fun main() {
    println("--- Probando Sealed Interface ---")
    procesar(Resultado.Ok("Todo salió bien"))
    procesar(Resultado.Fallo("Algo salió mal"))

    println("\n--- Probando Funciones de Extensión ---")
    println("¿'Kotlin es genial' es larga?: ${"Kotlin es genial".esLarga()}")
    println("Duplicar 5: ${5.duplicar()}")
    println("Primero de la lista: ${listOf("A", "B").primero()}")
    println("Precio con impuesto: ${100.0.impuesto()}")
    println("Invertir true: ${true.invertir()}")
}