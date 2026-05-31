package com.example.data.db.table

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime

object UserPrizesTable : Table("user_prizes") {

    val userId = integer("user_id").references(UsersTable.id)
    val prizeId = integer("prize_id").references(PrizesTable.id)

    val addedAt = datetime("added_at")

    override val primaryKey = PrimaryKey(userId, prizeId)
}