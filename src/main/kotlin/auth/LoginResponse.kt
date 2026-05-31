package com.example.auth

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val token: String
)