package com.example.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Laureate(
    val id: String,
    val fullName: String,
    val motivation: String,
    val country: String
)