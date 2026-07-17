package com.jetbrains.kmpapp.rickmorty.domain

data class Personaje(
    val id: Int,
    val nombre: String,
    val estado: String,
    val especie: String,
    val imagen: String,
    val ubicacion: String
)
