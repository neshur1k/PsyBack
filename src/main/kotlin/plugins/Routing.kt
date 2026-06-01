package com.example.plugins

import com.example.auth.authRoutes
import com.example.data.repository.NobelRepositoryImpl
import com.example.routes.prizeRoutes
import io.ktor.server.application.*
import io.ktor.server.auth.authenticate
import io.ktor.server.response.*
import io.ktor.server.routing.*
import com.example.routes.userRoutes
import com.example.routes.userPrizeRoutes
import io.ktor.server.plugins.openapi.openAPI
import io.ktor.server.plugins.swagger.swaggerUI

fun Application.configureRouting() {

    val repository =
        NobelRepositoryImpl()

    routing {

        openAPI(
            path = "openapi",
            swaggerFile = "openapi/documentation.yaml"
        )

        swaggerUI(
            path = "swagger",
            swaggerFile = "openapi/documentation.yaml"
        )

        get("/") {
            call.respondText(
                "Nobel API is running"
            )
        }

        authRoutes()

        authenticate {

            prizeRoutes(repository)

            userRoutes()

            userPrizeRoutes()
        }
    }
}