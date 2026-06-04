package com.example.models

import java.time.LocalDateTime


data class Article(
    val id: Int,
    val title: String,
    val content: String,
    val category: ArticleCategory,
    val authorId: Int,
    val createdAt: LocalDateTime
)