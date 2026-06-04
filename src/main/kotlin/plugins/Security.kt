package com.example.plugins

import com.example.security.JwtConfig
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*

fun Application.configureSecurity() {

    install(Authentication) {

        jwt {

            verifier(
                com.auth0.jwt.JWT
                    .require(
                        JwtConfig.algorithm()
                    )
                    .withAudience(
                        JwtConfig.audience()
                    )
                    .withIssuer(
                        JwtConfig.issuer()
                    )
                    .build()
            )

            validate { credential ->

                if (
                    credential.payload
                        .getClaim("userId")
                        .asInt() != null
                ) {
                    JWTPrincipal(
                        credential.payload
                    )
                } else {
                    null
                }
            }
        }
    }
}