package com.jetbrains.kmpapp.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class ITunesResponseDto(
    val resultCount: Int,
    val results: List<ITunesResultDto>
)

@Serializable
data class ITunesResultDto(
    val trackId: Long? = null,
    val collectionId: Long? = null,
    val trackName: String? = null,
    val collectionName: String? = null,
    val artistName: String? = null,
    val artworkUrl100: String? = null,
    val trackPrice: Double? = null,
    val collectionPrice: Double? = null,
    val releaseDate: String? = null,
    val primaryGenreName: String? = null,
    val kind: String? = null,
    val wrapperType: String? = null,
    val longDescription: String? = null,
    val description: String? = null,
    val currency: String? = null,
    val country: String? = null,
    val previewUrl: String? = null
)
