package com.example.repository

import com.example.dto.ArticleWithAuthorResponse
import com.example.models.Article

interface ArticleRepository {

    fun createArticle(
        title: String,
        content: String,
        category: String,
        authorId: Int
    ): Article

    fun getAllArticles(): List<Article>

    fun getArticleById(
        id: Int
    ): Article?

    fun updateArticle(
        id: Int,
        title: String,
        content: String,
        category: String
    ): Boolean

    fun deleteArticle(
        id: Int
    ): Boolean

    fun getArticlesByCategory(
        category: String
    ): List<Article>

    fun getAllArticlesWithAuthors(): List<ArticleWithAuthorResponse>

    fun getArticlesWithAuthorsByCategory(
        category: String
    ): List<ArticleWithAuthorResponse>
}