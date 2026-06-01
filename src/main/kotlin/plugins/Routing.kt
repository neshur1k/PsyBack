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

fun Application.configureRouting() {

    val repository =
        NobelRepositoryImpl()

    routing {

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