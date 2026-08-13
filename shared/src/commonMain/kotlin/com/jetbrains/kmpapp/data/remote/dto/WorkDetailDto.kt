package com.jetbrains.kmpapp.data.remote.dto

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class WorkDetailDto(
    val key: String,
    val title: String,
    val description: JsonElement? = null,
    val authors: List<AuthorEntryDto> = emptyList(),
    val covers: List<Long> = emptyList(),
    val first_publish_date: String? = null,
    val subjects: List<String> = emptyList()
)

@Serializable
data class AuthorEntryDto(
    val author: AuthorIdDto,
    val type: AuthorTypeDto? = null
)

@Serializable
data class AuthorIdDto(
    val key: String
)

@Serializable
data class AuthorTypeDto(
    val key: String
)
