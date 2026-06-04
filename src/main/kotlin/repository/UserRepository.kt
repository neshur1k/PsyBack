package com.example.repository

import com.example.models.User


interface UserRepository {

    fun createUser(
        login: String,
        passwordHash: String,
        nickname: String
    ): User

    fun findByLogin(
        login: String
    ): User?

    fun findById(
        id: Int
    ): User?
}