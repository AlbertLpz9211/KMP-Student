package com.jetbrains.kmpapp.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class SearchResponseDto(
    val numFound: Int,
    val docs: List<BookDto> = emptyList()
)

@Serializable
data class BookDto(
    val key: String,
    val title: String,
    val subtitle: String? = null,
    val author_name: List<String> = emptyList(),
    val first_publish_year: Int? = null,
    val cover_i: Long? = null,
    val ratings_average: Double? = null,
    val subject: List<String> = emptyList()
)
