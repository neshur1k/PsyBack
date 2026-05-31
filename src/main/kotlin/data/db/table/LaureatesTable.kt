package com.example.data.db.table

import org.jetbrains.exposed.sql.Table

object LaureatesTable : Table("laureates") {
    val id = integer("id").autoIncrement()
    val prizeId = integer("prize_id").references(PrizesTable.id)

    val fullName = varchar("full_name", 255)
    val portion = varchar("portion", 10)
    val motivation = text("motivation")
    val portraitUrl = varchar("portrait_url", 500).nullable()

    override val primaryKey = PrimaryKey(id)
}