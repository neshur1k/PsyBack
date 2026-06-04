package com.example.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import java.util.*

object JwtConfig {

    private const val SECRET =
        "super_secret_key_for_psychology_app"

    private const val ISSUER =
        "psychology-app"

    private const val AUDIENCE =
        "psychology-users"

    private const val EXPIRES_IN =
        1000L * 60 * 60 * 24 * 7

    private val algorithm =
        Algorithm.HMAC256(SECRET)

    fun generateToken(
        userId: Int,
        role: String
    ): String {

        return JWT.create()
            .withAudience(AUDIENCE)
            .withIssuer(ISSUER)
            .withClaim("userId", userId)
            .withClaim("role", role)
            .withExpiresAt(
                Date(
                    System.currentTimeMillis() + EXPIRES_IN
                )
            )
            .sign(algorithm)
    }

    fun algorithm() = algorithm

    fun issuer() = ISSUER

    fun audience() = AUDIENCE
}