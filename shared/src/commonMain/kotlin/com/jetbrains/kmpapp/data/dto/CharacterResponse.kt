package com.jetbrains.kmpapp.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class CharacterResponse(
    val info: InfoDto,
    val results: List<CharacterDto>
)

@Serializable
data class InfoDto(
    val count: Int,
    val pages: Int,
    val next: String?,
    val prev: String?
)

@Serializable
data class CharacterDto(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val gender: String,
    val image: String // Añadido para poder mostrar la imagen en la UI
)