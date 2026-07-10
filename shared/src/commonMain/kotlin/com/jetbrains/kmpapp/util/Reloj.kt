package com.jetbrains.kmpapp.util



// Declaramos la promesa de la función
expect fun epochMillis(): Long

// Clase que consume nuestra función de bajo nivel
class Reloj {
    fun ahora(): Long = epochMillis()
}