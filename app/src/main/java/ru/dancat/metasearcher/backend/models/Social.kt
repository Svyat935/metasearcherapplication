package ru.dancat.metasearcher.backend.models

import kotlinx.serialization.Serializable

@Serializable
data class Social (
    val id: String,
    val youtube: String,
    val whatsApp: String,
    val vk: String,
    val telegram: String
)