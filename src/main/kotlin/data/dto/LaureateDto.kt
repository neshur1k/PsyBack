package com.example.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class LaureateDto(
    val fullName: FullNameDto? = null,
    val motivation: MotivationDto? = null,
    val portraitUrl: String? = null,
    val portion: String? = null
)