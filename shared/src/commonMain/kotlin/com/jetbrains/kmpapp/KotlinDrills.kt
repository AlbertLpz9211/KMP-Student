package com.jetbrains.kmpapp

// Data class: guarda datos + te da copy() e igualdad gratis
data class Pelicula(val id: Int, val titulo: String, val rating: Double)

// Sealed: estados EXCLUSIVOS (o cargando, o éxito, o error). Lo usaremos TODO el curso.
sealed interface Estado<out T> {
    data object Cargando : Estado<Nothing>
    data class Exito<T>(val data: T) : Estado<T>
    data class Error(val mensaje: String) : Estado<Nothing>
}

// Función de extensión: le agrego un método a un tipo existente
fun Double.aEstrellas(): String = "★".repeat((this / 2).toInt())

fun demoKotlin() {
    val p = Pelicula(1, "Dune", 8.4)
    val mejor = p.copy(rating = 9.0)                 // copia cambiando 1 campo
    println("${p.titulo} ${p.rating.aEstrellas()}")  // usa la extensión
    println(mejor)
}
