package com.example.dto

import kotlinx.serialization.Serializable

@Serializable
data class ArticleResponse(
    val id: Int,
    val title: String,
    val content: String,
    val category: String,
    val authorId: Int,
    val authorNickname: String,
    val createdAt: String
)