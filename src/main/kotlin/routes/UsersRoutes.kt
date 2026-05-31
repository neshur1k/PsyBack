package com.example.routes

import com.example.data.db.table.UsersTable
import com.example.data.model.UserResponse
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.principal
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

fun Route.userRoutes() {

    authenticate {

        get("/users/me") {

            val principal =
                call.principal<JWTPrincipal>()

            val username =
                principal
                    ?.payload
                    ?.getClaim("username")
                    ?.asString()

            val user = transaction {

                UsersTable
                    .selectAll()
                    .firstOrNull {
                        it[UsersTable.username] == username
                    }
            }

            if (user == null) {
                call.respondText(
                    "User not found"
                )
            } else {
                call.respond(
                    UserResponse(
                        id = user[UsersTable.id],
                        username = user[UsersTable.username],
                        role = user[UsersTable.role]
                    )
                )
            }
        }
    }
}