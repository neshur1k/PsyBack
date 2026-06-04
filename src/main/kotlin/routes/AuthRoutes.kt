package com.example.routes

import com.example.dto.RegisterRequest
import com.example.repository.UserRepository
import com.example.security.PasswordHasher
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.authRoutes(
    repository: UserRepository
) {
    post("/register") {

        val request =
            call.receive<RegisterRequest>()

        val existingUser =
            repository.findByLogin(
                request.login
            )

        if (existingUser != null) {

            call.respond(
                HttpStatusCode.Conflict,
                "User already exists"
            )

            return@post
        }

        repository.createUser(
            login = request.login,
            passwordHash =
                PasswordHasher.hash(
                    request.password
                ),
            nickname = request.nickname
        )

        call.respond(
            HttpStatusCode.Created,
            "User created"
        )
    }
}