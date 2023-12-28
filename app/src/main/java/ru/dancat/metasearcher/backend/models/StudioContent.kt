package ru.dancat.metasearcher.backend.models

import kotlinx.serialization.Serializable

@Serializable
data class StudioRequest(
    val pageNumber: Int = 0,
    val pageSize: Int = 10,
    val sortBy: String = "id",
    val searchCriteria: String = "",
    val orderDirection: String = "ASC",
    val stylesList: List<String> = emptyList(),
    val minimalRate: Double = 0.0,
    val metroList: List<String> = emptyList()
)

@Serializable
data class StudioContent(
    val id: String,
    val name: String?,
    val address: String?,
    val email: String?,
    val website: String?,
    val social: String?,
    val description: String,
    val rate: Double,
    val feedback: List<String>,
    val photos: List<String>,
    val status: Boolean,
    val metros: List<String>,
    val styles: List<String>,
    val inn: String
)

@Serializable
data class StudioLite(
    val id: String,
    val name: String,
    val rate: Double
)

@Serializable
data class Pageable(
    val pageNumber: Int,
    val pageSize: Int,
    val sort: Sort,
    val offset: Int,
    val unpaged: Boolean,
    val paged: Boolean
)

@Serializable
data class Sort(
    val empty: Boolean,
    val sorted: Boolean,
    val unsorted: Boolean
)

@Serializable
data class StudiosResponse(
    val content: List<StudioLite>,
    val pageable: Pageable,
    val last: Boolean,
    val totalPages: Int,
    val totalElements: Int,
    val size: Int,
    val number: Int,
    val sort: Sort,
    val first: Boolean,
    val numberOfElements: Int,
    val empty: Boolean
)
