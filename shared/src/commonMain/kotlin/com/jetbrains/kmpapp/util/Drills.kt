package com.jetbrains.kmpapp.util

// Sealed interface
sealed interface Resultado {
    data class Exitoso(val datos: String) : Resultado
    data class Falla(val mensaje: String) : Resultado
}

fun procesarResultado(res: Resultado): String {
    return when (res) {
        is Resultado.Exitoso -> "¡Éxito!: ${res.datos}"
        is Resultado.Falla -> "Error: ${res.mensaje}"
    }
}

// 5 funciones de extensión
fun String.gritar() = this.uppercase() + "!!!"

fun Int.esPar() = this % 2 == 0

fun <T> List<T>.segundoElementoOrNull() = if (this.size >= 2) this[1] else null

fun String.limpiar() = this.trim()

fun Int.duplicar() = this * 2

fun main() {
    testDrills()
}

fun testDrills() {
    println("--- Probando Resultado ---")
    val ok = Resultado.Exitoso("Datos de la API")
    val error = Resultado.Falla("No hay internet")
    println(procesarResultado(ok))
    println(procesarResultado(error))

    println("\n--- Probando Extensiones ---")
    println("hola".gritar())
    println("¿Es 4 par?: ${4.esPar()}")
    println("Lista segundo elemento: ${listOf("A", "B", "C").segundoElementoOrNull()}")
    println("Texto limpio: ${"  hola  ".limpiar()}")
    println("Duplicar 5: ${5.duplicar()}")
}
