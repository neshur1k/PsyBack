package com.example.repository

import com.example.models.User
import com.example.models.UserRole
import com.example.tables.UsersTable
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class UserRepositoryImpl : UserRepository {

    private fun ResultRow.toUser(): User {

        return User(
            id = this[UsersTable.id],
            login = this[UsersTable.login],
            passwordHash = this[UsersTable.passwordHash],
            nickname = this[UsersTable.nickname],
            bio = this[UsersTable.bio],
            role = UserRole.valueOf(
                this[UsersTable.role]
            )
        )
    }

    override fun createUser(
        login: String,
        passwordHash: String,
        nickname: String
    ): User = transaction {

        val id = UsersTable.insert {

            it[UsersTable.login] = login
            it[UsersTable.passwordHash] = passwordHash
            it[UsersTable.nickname] = nickname
            it[bio] = ""
            it[role] = UserRole.USER.name

        } get UsersTable.id

        User(
            id = id,
            login = login,
            passwordHash = passwordHash,
            nickname = nickname,
            bio = "",
            role = UserRole.USER
        )
    }

    override fun findByLogin(
        login: String
    ): User? = transaction {

        UsersTable
            .selectAll()
            .where {
                UsersTable.login eq login
            }
            .singleOrNull()
            ?.toUser()
    }

    override fun findById(
        id: Int
    ): User? = transaction {

        UsersTable
            .selectAll()
            .where {
                UsersTable.id eq id
            }
            .singleOrNull()
            ?.toUser()
    }
}

