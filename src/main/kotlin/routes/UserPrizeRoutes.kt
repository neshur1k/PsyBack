package com.example.routes

import com.example.data.db.table.UserPrizesTable
import com.example.data.db.table.UsersTable
import io.ktor.http.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDateTime

fun Route.userPrizeRoutes() {

    authenticate {

        post("/users/me/prizes/{prizeId}") {

            val principal =
                call.principal<JWTPrincipal>()

            val username =
                principal
                    ?.payload
                    ?.getClaim("username")
                    ?.asString()

            val prizeId =
                call.parameters["prizeId"]
                    ?.toIntOrNull()

            if (prizeId == null) {
                call.respond(
                    HttpStatusCode.BadRequest
                )
                return@post
            }

            val userId = transaction {

                UsersTable
                    .selectAll()
                    .first {
                        it[UsersTable.username] == username
                    }[UsersTable.id]
            }

            transaction {

                UserPrizesTable.insert {

                    it[UserPrizesTable.userId] =
                        userId

                    it[UserPrizesTable.prizeId] =
                        prizeId

                    it[addedAt] =
                        LocalDateTime.now()
                }
            }

            call.respondText(
                "Prize added to favorites"
            )
        }
    }
}