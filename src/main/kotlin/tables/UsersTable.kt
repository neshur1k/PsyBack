package com.example.tables

import org.jetbrains.exposed.sql.Table

object UsersTable : Table("users") {

    val id = integer("id").autoIncrement()

    val login = varchar("login", 50).uniqueIndex()

    val passwordHash = varchar("password_hash", 255)

    val nickname = varchar("nickname", 50)

    val bio = text("bio")

    val role = varchar("role", 20)

    override val primaryKey = PrimaryKey(id)
}