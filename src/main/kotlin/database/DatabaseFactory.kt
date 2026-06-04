package com.example.database

import com.example.tables.ArticlesTable
import com.example.tables.UsersTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {

    fun init() {

        Database.connect(
            url =
                "jdbc:postgresql://ep-rapid-rice-apccry4d-pooler.c-7.us-east-1.aws.neon.tech/neondb?sslmode=require",

            driver = "org.postgresql.Driver",

            user = "neondb_owner",

            password = "npg_cj8gm7MsakyB"
        )

        transaction {
            SchemaUtils.create(
                UsersTable,
                ArticlesTable
            )
        }
    }
}