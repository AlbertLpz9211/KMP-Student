package com.jetbrains.kmpapp.data

import kotlinx.serialization.Serializable

@Serializable
data class MuseumObject(
    val objectID: Int,
    val title: String,
    val artistDisplayName: String,
    val objectDate: String,
    val primaryImageSmall: String,
    val dimensions: String,
    val medium: String,
    val department: String,
    val repository: String,
    val creditLine: String
)
