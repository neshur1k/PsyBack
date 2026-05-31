package com.example.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.example.auth.JwtConfig
import io.ktor.server.application.Application
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.jwt.jwt
import io.ktor.server.auth.Authentication
import io.ktor.server.application.install

fun Application.configureSecurity() {

    install(Authentication) {

        jwt {

            realm = "Nobel API"

            verifier(
                JWT
                    .require(
                        Algorithm.HMAC256(
                            JwtConfig.SECRET
                        )
                    )
                    .withIssuer(
                        JwtConfig.ISSUER
                    )
                    .withAudience(
                        JwtConfig.AUDIENCE
                    )
                    .build()
            )

            validate {
                JWTPrincipal(it.payload)
            }
        }
    }
}