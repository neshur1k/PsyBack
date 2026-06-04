package com.example.repository

import com.example.models.Article
import com.example.models.ArticleCategory
import com.example.tables.ArticlesTable
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDateTime

class ArticleRepositoryImpl : ArticleRepository {
    private fun ResultRow.toArticle(): Article {

        return Article(
            id = this[ArticlesTable.id],
            title = this[ArticlesTable.title],
            content = this[ArticlesTable.content],
            category = ArticleCategory.valueOf(
                this[ArticlesTable.category]
            ),
            authorId = this[ArticlesTable.authorId],
            createdAt = this[ArticlesTable.createdAt]
        )
    }

    override fun createArticle(
        title: String,
        content: String,
        category: String,
        authorId: Int
    ): Article = transaction {

        val now = LocalDateTime.now()

        val id = ArticlesTable.insert {

            it[ArticlesTable.title] = title
            it[ArticlesTable.content] = content
            it[ArticlesTable.category] = category
            it[ArticlesTable.authorId] = authorId
            it[ArticlesTable.createdAt] = now

        } get ArticlesTable.id

        Article(
            id = id,
            title = title,
            content = content,
            category = ArticleCategory.valueOf(category),
            authorId = authorId,
            createdAt = now
        )
    }

    override fun getAllArticles(): List<Article> =
        transaction {

            ArticlesTable
                .selectAll()
                .map {
                    it.toArticle()
                }
        }

    override fun getArticleById(
        id: Int
    ): Article? = transaction {

        ArticlesTable
            .selectAll()
            .where {
                ArticlesTable.id eq id
            }
            .singleOrNull()
            ?.toArticle()
    }
}