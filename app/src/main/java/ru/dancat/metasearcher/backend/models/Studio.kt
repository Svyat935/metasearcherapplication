package ru.dancat.metasearcher.backend.models

import kotlinx.serialization.Serializable

@Serializable
data class StudioRequest (
    val pageNumber: Int = 1,
    val pageSize: Int = 10,
    val sortBy: String = "id",
    val orderDirection: String = "ASC",
    val stylesList: List<String>,
    val minimalRate: Int,
    val metroList: List<String>
)

@Serializable
data class Studio(
    val id: String,
    val name: String,
    val address: String,
    val email: String,
    val website: String,
    val social: String,
    val description: String,
    val rate: Int,
    val feedback: List<String>,
//    val photos: List<String>
    val status: Boolean,
    val metros: List<String>,
    val styles: List<String>,
    val inn: String
)