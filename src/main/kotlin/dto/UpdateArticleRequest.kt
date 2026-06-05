package com.example.dto

import kotlinx.serialization.Serializable

@Serializable
data class UpdateArticleRequest(
    val title: String,
    val content: String,
    val category: String
)