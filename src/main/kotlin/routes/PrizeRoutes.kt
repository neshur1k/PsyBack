package com.example.routes

import com.example.domain.repository.NobelRepository
import io.ktor.http.HttpStatusCode
import io.ktor.server.auth.authenticate
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get

fun Route.prizeRoutes(
    repository: NobelRepository
) {

    authenticate {

        get("/prizes") {

            call.respond(
                repository.getPrizes()
            )
        }

        get(
            "/prizes/{year}/{category}"
        ) {

            val year =
                call.parameters["year"]!!

            val category =
                call.parameters["category"]!!

            val prize =
                repository.getPrize(
                    year,
                    category
                )

            if (prize == null) {

                call.respond(
                    HttpStatusCode.NotFound
                )

            } else {

                call.respond(prize)
            }
        }

        get(
            "/prizes/{year}/{category}/laureates"
        ) {

            val year =
                call.parameters["year"]!!

            val category =
                call.parameters["category"]!!

            call.respond(
                repository.getLaureates(
                    year,
                    category
                )
            )
        }
    }
}