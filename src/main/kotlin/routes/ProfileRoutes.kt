package com.example.routes

import com.example.dto.UpdateProfileRequest
import com.example.dto.UserResponse
import com.example.repository.UserRepository
import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.receive
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.profileRoutes(
    repository: UserRepository
) {

    authenticate {

        get("/me") {

            val principal =
                call.principal<JWTPrincipal>()

            val userId =
                principal
                    ?.payload
                    ?.getClaim("userId")
                    ?.asInt()

            if (userId == null) {

                call.respond(
                    HttpStatusCode.Unauthorized
                )

                return@get
            }

            val user =
                repository.findById(userId)

            if (user == null) {

                call.respond(
                    HttpStatusCode.NotFound
                )

                return@get
            }

            call.respond(
                UserResponse(
                    id = user.id,
                    login = user.login,
                    nickname = user.nickname,
                    bio = user.bio,
                    role = user.role.name
                )
            )
        }

        put("/profile") {

            val principal =
                call.principal<JWTPrincipal>()

            val userId =
                principal
                    ?.payload
                    ?.getClaim("userId")
                    ?.asInt()

            if (userId == null) {

                call.respond(
                    HttpStatusCode.Unauthorized
                )

                return@put
            }

            val request =
                call.receive<UpdateProfileRequest>()

            repository.updateProfile(
                userId = userId,
                nickname = request.nickname,
                bio = request.bio
            )

            call.respond(
                HttpStatusCode.OK
            )
        }
    }
}