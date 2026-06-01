package com.example.data.model

import kotlinx.serialization.Serializable

@Serializable
data class FavoritePrizeResponse(
    val id: Int,
    val awardYear: String,
    val category: String,
    val fullName: String
)