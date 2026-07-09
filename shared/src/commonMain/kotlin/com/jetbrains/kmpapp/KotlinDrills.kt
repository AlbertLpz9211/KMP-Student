package com.jetbrains.kmpapp

data class Pelicula(val id: Int, val titulo: String, val rating: Double)

sealed interface Resultado<out T> {
    data class Ok<T>(val valor: T) : Resultado<T>
    data class Fallo(val mensaje: String) : Resultado<Nothing>
}

fun textoResultado(resultado: Resultado<String>): String =
    when (resultado) {
        is Resultado.Ok -> "OK: ${resultado.valor}"
        is Resultado.Fallo -> "Fallo: ${resultado.mensaje}"
    }

fun String.limpiar(): String = trim()

fun String.esTituloValido(): Boolean = trim().isNotEmpty()

fun Int.esPositivo(): Boolean = this > 0

fun Double.aEstrellas(): String = "★".repeat((this / 2).toInt())

fun List<String>.total(): Int = size

fun main() {
    val pelicula = Pelicula(1, "Dune", 8.4)
    val resultado: Resultado<String> = Resultado.Ok(pelicula.titulo)

    println(pelicula)
    println("  Dune  ".limpiar())
    println("Dune".esTituloValido())
    println(5.esPositivo())
    println(pelicula.rating.aEstrellas())
    println(listOf("Dune", "Batman", "Avatar").total())
    println(textoResultado(resultado))
    println(textoResultado(Resultado.Fallo("No se pudo cargar")))
}
