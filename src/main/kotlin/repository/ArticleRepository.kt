package com.example.repository

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
}