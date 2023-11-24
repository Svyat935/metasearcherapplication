package ru.dancat.metasearcher.backend.models

import kotlinx.serialization.Serializable

@Serializable
data class Metro(
    val id: String,
    val name: String,
    val studios: List<String>
)