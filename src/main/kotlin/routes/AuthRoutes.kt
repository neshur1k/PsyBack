package com.example.routes

import com.example.dto.AuthResponse
import com.example.dto.LoginRequest
import com.example.dto.RegisterRequest
import com.example.repository.UserRepository
import com.example.security.JwtConfig
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

    post("/login") {

        val request =
            call.receive<LoginRequest>()

        val user =
            repository.findByLogin(
                request.login
            )

        if (user == null) {

            call.respond(
                HttpStatusCode.Unauthorized,
                "Invalid login or password"
            )

            return@post
        }

        val isPasswordCorrect =
            PasswordHasher.verify(
                request.password,
                user.passwordHash
            )

        if (!isPasswordCorrect) {

            call.respond(
                HttpStatusCode.Unauthorized,
                "Invalid login or password"
            )

            return@post
        }

        val token =
            JwtConfig.generateToken(
                user.id,
                user.role.name
            )

        call.respond(
            AuthResponse(token)
        )
    }
}