package com.example.models

data class User(
    val id: Int,
    val login: String,
    val passwordHash: String,
    val nickname: String,
    val bio: String,
    val role: UserRole
)