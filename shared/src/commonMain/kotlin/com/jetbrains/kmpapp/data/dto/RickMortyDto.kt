package com.jetbrains.kmpapp.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

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
    val type: String,
    val gender: String,
    @SerialName("image") val imageUrl: String,
    val location: LocationRefDto? = null,
    val origin: LocationRefDto? = null
)

@Serializable
data class LocationRefDto(
    val name: String,
    val url: String
)
