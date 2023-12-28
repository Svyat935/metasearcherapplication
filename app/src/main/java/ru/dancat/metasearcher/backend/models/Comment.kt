package ru.dancat.metasearcher.backend.models

import kotlinx.serialization.Serializable

@Serializable
data class Comment(
    val id: String,
    val username: String,
    val comment: String,
    val rate: Double,
    val studio: String,
)