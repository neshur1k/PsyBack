package com.example

import com.example.database.DatabaseFactory
import com.example.plugins.configureSecurity
import com.example.repository.UserRepositoryImpl
import com.example.routes.authRoutes
import io.ktor.server.application.*
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.plugins.contentnegotiation.*


fun Application.module() {

    install(ContentNegotiation) {
        json()
    }

    DatabaseFactory.init()

    configureSecurity()

    val userRepository = UserRepositoryImpl()

    routing {

        get("/") {
            call.respondText(
                "Server is running"
            )
        }

        authRoutes(
            repository = userRepository
        )
    }

}