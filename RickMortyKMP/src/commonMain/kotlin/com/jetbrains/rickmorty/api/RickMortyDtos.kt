package com.jetbrains.rickmorty.api

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class CharacterPageDto(
    val info: PageInfoDto,
    val results: List<CharacterDto>
)

@Serializable
data class PageInfoDto(
    val count: Int,
    val pages: Int,
    val next: String? = null,
    val prev: String? = null
)

@Serializable
data class CharacterDto(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val gender: String,
    val image: String,
    val location: LocationRefDto
)

@Serializable
data class LocationRefDto(
    val name: String,
    val url: String
)
