package com.example.models

import kotlinx.serialization.Serializable

@Serializable
enum class ArticleCategory {
    ANXIETY,
    STRESS,
    MEDITATION,
    MOTIVATION,
    SELF_ESTEEM
}