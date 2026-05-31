package com.example.domain.repository

import com.example.data.model.Laureate
import com.example.data.model.Prize

interface NobelRepository {

    fun getPrizes(): List<Prize>

    fun getPrize(
        year: String,
        category: String
    ): Prize?

    fun getLaureates(
        year: String,
        category: String
    ): List<Laureate>
}