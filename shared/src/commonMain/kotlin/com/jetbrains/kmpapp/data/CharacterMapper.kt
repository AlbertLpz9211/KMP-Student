package com.jetbrains.kmpapp.data

import com.jetbrains.kmpapp.data.dto.CharacterDto
import com.jetbrains.kmpapp.domain.Personaje

fun CharacterDto.toDomain(): Personaje {
    return Personaje(
        id = id,
        nombre = name,
        estado = status,
        especie = species,
        imagen = imageUrl,
        ubicacion = location?.name ?: "Unknown"
    )
}
