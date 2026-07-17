package com.jetbrains.rickmorty.domain

import com.jetbrains.rickmorty.api.CharacterDto

data class Personaje(
    val id: Int,
    val nombre: String,
    val estado: String,
    val especie: String,
    val imagen: String,
    val ubicacion: String
)

fun CharacterDto.toDomain(): Personaje {
    return Personaje(
        id = id,
        nombre = name,
        estado = status,
        especie = species,
        imagen = image,
        ubicacion = location.name
    )
}
