package com.example.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class NobelPrizeDto(
    val awardYear: String,
    val category: CategoryDto,
    val laureates: List<LaureateDto>? = null
)