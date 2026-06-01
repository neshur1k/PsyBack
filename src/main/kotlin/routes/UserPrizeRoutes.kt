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
import com.example.data.db.table.PrizesTable
import com.example.data.model.FavoritePrizeResponse
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.and

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

        get("/users/me/prizes") {

            val principal =
                call.principal<JWTPrincipal>()

            val username =
                principal
                    ?.payload
                    ?.getClaim("username")
                    ?.asString()

            val userId = transaction {

                UsersTable
                    .selectAll()
                    .first {
                        it[UsersTable.username] == username
                    }[UsersTable.id]
            }

            val prizes: List<FavoritePrizeResponse> = transaction {

                (UserPrizesTable innerJoin PrizesTable)
                    .select(UserPrizesTable.userId eq userId)
                    .map {
                        FavoritePrizeResponse(
                            id = it[PrizesTable.id],
                            awardYear = it[PrizesTable.awardYear],
                            category = it[PrizesTable.category],
                            fullName = it[PrizesTable.fullName]
                        )
                    }
            }

            call.respond(prizes)
        }
    }
}