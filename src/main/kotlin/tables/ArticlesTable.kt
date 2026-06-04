package com.example.tables

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime

object ArticlesTable : Table("articles") {

    val id = integer("id").autoIncrement()

    val title = varchar("title", 200)

    val content = text("content")

    val category = varchar("category", 50)

    val authorId =
        integer("author_id")
            .references(UsersTable.id)

    val createdAt = datetime("created_at")

    override val primaryKey =
        PrimaryKey(id)
}