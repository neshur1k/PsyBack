package com.example.dto

import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    val id: Int,
    val login: String,
    val nickname: String,
    val bio: String,
    val role: String
)