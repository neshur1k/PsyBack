package com.example.data.db.table

import org.jetbrains.exposed.sql.Table

object PrizesTable : Table("prizes") {
    val id = integer("id").autoIncrement()
    val awardYear = varchar("award_year", 10)
    val category = varchar("category", 50)
    val fullName = varchar("full_name", 255)
    val motivation = text("motivation")
    val detailLink = varchar("detail_link", 500)

    override val primaryKey = PrimaryKey(id)
}