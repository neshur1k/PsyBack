package com.example.repository

import com.example.models.Article
import com.example.models.ArticleCategory
import com.example.tables.ArticlesTable
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDateTime
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.update
import org.jetbrains.exposed.sql.and
import com.example.dto.ArticleWithAuthorResponse
import com.example.tables.UsersTable
import org.jetbrains.exposed.sql.JoinType

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

    override fun updateArticle(
        id: Int,
        title: String,
        content: String,
        category: String
    ): Boolean = transaction {

        ArticlesTable.update(
            { ArticlesTable.id eq id }
        ) {

            it[ArticlesTable.title] = title
            it[ArticlesTable.content] = content
            it[ArticlesTable.category] = category
        } > 0
    }

    override fun deleteArticle(
        id: Int
    ): Boolean = transaction {

        ArticlesTable.deleteWhere {
            ArticlesTable.id eq id
        } > 0
    }

    override fun getArticlesByCategory(
        category: String
    ): List<Article> = transaction {

        ArticlesTable
            .selectAll()
            .where {
                ArticlesTable.category eq category
            }
            .map {
                it.toArticle()
            }
    }

    override fun getAllArticlesWithAuthors(): List<ArticleWithAuthorResponse> =
        transaction {

            ArticlesTable
                .join(
                    UsersTable,
                    JoinType.INNER,
                    ArticlesTable.authorId,
                    UsersTable.id
                )
                .selectAll()
                .map {

                    ArticleWithAuthorResponse(
                        id = it[ArticlesTable.id],
                        title = it[ArticlesTable.title],
                        content = it[ArticlesTable.content],
                        category = it[ArticlesTable.category],
                        authorId = it[ArticlesTable.authorId],
                        authorNickname = it[UsersTable.nickname],
                        createdAt = it[ArticlesTable.createdAt].toString()
                    )
                }
        }

    override fun getArticlesWithAuthorsByCategory(
        category: String
    ): List<ArticleWithAuthorResponse> =
        transaction {

            ArticlesTable
                .join(
                    UsersTable,
                    JoinType.INNER,
                    ArticlesTable.authorId,
                    UsersTable.id
                )
                .selectAll()
                .where {
                    ArticlesTable.category eq category
                }
                .map {

                    ArticleWithAuthorResponse(
                        id = it[ArticlesTable.id],
                        title = it[ArticlesTable.title],
                        content = it[ArticlesTable.content],
                        category = it[ArticlesTable.category],
                        authorId = it[ArticlesTable.authorId],
                        authorNickname = it[UsersTable.nickname],
                        createdAt = it[ArticlesTable.createdAt].toString()
                    )
                }
        }

}