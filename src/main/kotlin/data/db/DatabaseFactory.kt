package com.example.data.db

import com.example.data.db.table.LaureatesTable
import com.example.data.db.table.PrizesTable
import com.example.data.db.table.UserPrizesTable
import com.example.data.db.table.UsersTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll

object DatabaseFactory {

    fun init() {
        Database.connect(
            url = "jdbc:postgresql://ep-small-frog-ab1zfj6b.eu-west-2.aws.neon.tech/neondb?sslmode=require",
            driver = "org.postgresql.Driver",
            user = "neondb_owner",
            password = "npg_Dp3rBin7KJkm"
        )

        transaction {
            SchemaUtils.create(
                UsersTable,
                PrizesTable,
                LaureatesTable,
                UserPrizesTable
            )

            println("TABLES CREATED / VERIFIED")
        }
    }
}