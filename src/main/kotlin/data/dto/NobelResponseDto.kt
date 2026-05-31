package com.example.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class NobelResponseDto(
    val nobelPrizes: List<NobelPrizeDto>
)