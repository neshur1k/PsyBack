package com.example.routes

import com.example.dto.ArticleResponse
import com.example.dto.CreateArticleRequest
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
    }

    get("/articles") {

        val articles = articleRepository.getAllArticles()

        val response = articles.map { article ->

            val author = userRepository.findById(article.authorId)

            ArticleResponse(
                id = article.id,
                title = article.title,
                content = article.content,
                category = article.category.name,
                authorId = article.authorId,
                authorNickname = author?.nickname ?: "Unknown",
                createdAt = article.createdAt.toString()
            )
        }

        call.respond(response)
    }
}