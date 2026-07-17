package examen.rickmorty.data.api

import examen.rickmorty.domain.Personaje

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