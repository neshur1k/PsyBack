package com.example.auth

import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post
import java.util.Date
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.server.request.receiveText

fun Route.authRoutes() {

    println("AUTH ROUTES LOADED")

    post("/auth/login") {

        val request = call.receive<LoginRequest>()

        if (
            request.login == "admin" &&
            request.password == "1234"
        ) {
            println("logged in")
            val token = JWT.create()
                .withIssuer(
                    JwtConfig.ISSUER
                )
                .withAudience(
                    JwtConfig.AUDIENCE
                )
                .withClaim(
                    "username",
                    request.login
                )
                .withExpiresAt(
                    Date(
                        System.currentTimeMillis()
                                + 30 * 60 * 1000
                    )
                )
                .sign(
                    Algorithm.HMAC256(
                        JwtConfig.SECRET
                    )
                )

            call.respond(
                LoginResponse(token)
            )

        } else {
            println("not logged in")
            call.respond(
                HttpStatusCode.Unauthorized
            )
        }


    }
}