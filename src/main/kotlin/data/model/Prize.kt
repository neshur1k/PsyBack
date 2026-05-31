package com.example.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Prize(
    val year: String,
    val category: String,
    val laureates: List<Laureate>
)