package com.jetbrains.kmpapp.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BookResponse(
    val docs: List<BookDocDto>? = null,
)

@Serializable
data class BookDocDto(
    @SerialName("key") val id: String,
    val title: String,
    @SerialName("author_name") val authors: List<String>? = null,
    @SerialName("cover_i") val coverId: Int? = null,
    @SerialName("first_publish_year") val firstPublishYear: Int? = null,
    val subject: List<String>? = null,
)
