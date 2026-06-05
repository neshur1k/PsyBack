package com.example.routes

import com.example.dto.ArticleResponse
import com.example.dto.CreateArticleRequest
import com.example.dto.UpdateArticleRequest
import com.example.repository.ArticleRepository
import com.example.repository.UserRepository
import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.articleRoutes(
    articleRepository: ArticleRepository,
    userRepository: UserRepository
) {

    authenticate {

        post("/articles") {

            val principal = call.principal<JWTPrincipal>()

            val userId = principal
                ?.payload
                ?.getClaim("userId")
                ?.asInt()

            if (userId == null) {
                call.respond(HttpStatusCode.Unauthorized)
                return@post
            }

            val request = call.receive<CreateArticleRequest>()

            val article = articleRepository.createArticle(
                title = request.title,
                content = request.content,
                category = request.category,
                authorId = userId
            )

            val response = ArticleResponse(
                id = article.id,
                title = article.title,
                content = article.content,
                category = article.category.name,
                authorId = article.authorId,
                authorNickname = "Unknown",
                createdAt = article.createdAt.toString()
            )

            call.respond(
                HttpStatusCode.Created,
                response
            )
        }

        put("/articles/{id}") {

            val articleId =
                call.parameters["id"]?.toIntOrNull()

            if (articleId == null) {
                call.respond(HttpStatusCode.BadRequest)
                return@put
            }

            val principal =
                call.principal<JWTPrincipal>()

            val userId =
                principal
                    ?.payload
                    ?.getClaim("userId")
                    ?.asInt()

            if (userId == null) {
                call.respond(HttpStatusCode.Unauthorized)
                return@put
            }

            val article =
                articleRepository.getArticleById(articleId)

            if (article == null) {
                call.respond(HttpStatusCode.NotFound)
                return@put
            }

            if (article.authorId != userId) {
                call.respond(HttpStatusCode.Forbidden)
                return@put
            }

            val request =
                call.receive<UpdateArticleRequest>()

            articleRepository.updateArticle(
                id = articleId,
                title = request.title,
                content = request.content,
                category = request.category
            )

            call.respond(HttpStatusCode.OK)
        }

        delete("/articles/{id}") {

            val articleId =
                call.parameters["id"]?.toIntOrNull()

            if (articleId == null) {
                call.respond(HttpStatusCode.BadRequest)
                return@delete
            }

            val principal =
                call.principal<JWTPrincipal>()

            val userId =
                principal
                    ?.payload
                    ?.getClaim("userId")
                    ?.asInt()

            val role =
                principal
                    ?.payload
                    ?.getClaim("role")
                    ?.asString()

            if (userId == null) {
                call.respond(HttpStatusCode.Unauthorized)
                return@delete
            }

            val article =
                articleRepository.getArticleById(articleId)

            if (article == null) {
                call.respond(HttpStatusCode.NotFound)
                return@delete
            }

            val isOwner =
                article.authorId == userId

            val isAdmin =
                role == "ADMIN"

            if (!isOwner && !isAdmin) {
                call.respond(HttpStatusCode.Forbidden)
                return@delete
            }

            articleRepository.deleteArticle(articleId)

            call.respond(HttpStatusCode.OK)
        }
    }

    get("/articles") {

        val category =
            call.request.queryParameters["category"]

        val response =
            if (category.isNullOrBlank()) {

                articleRepository.getAllArticlesWithAuthors()

            } else {

                articleRepository
                    .getArticlesWithAuthorsByCategory(
                        category
                    )
            }

        call.respond(response)
    }

    get("/articles/{id}") {

        val articleId =
            call.parameters["id"]?.toIntOrNull()

        if (articleId == null) {
            call.respond(HttpStatusCode.BadRequest)
            return@get
        }

        val article =
            articleRepository.getArticleById(articleId)

        if (article == null) {
            call.respond(HttpStatusCode.NotFound)
            return@get
        }

        val author =
            userRepository.findById(article.authorId)

        val response = ArticleResponse(
            id = article.id,
            title = article.title,
            content = article.content,
            category = article.category.name,
            authorId = article.authorId,
            authorNickname = author?.nickname ?: "Unknown",
            createdAt = article.createdAt.toString()
        )

        call.respond(response)
    }
}