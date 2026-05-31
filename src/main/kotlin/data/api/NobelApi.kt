package com.example.data.api

import com.example.data.dto.NobelResponseDto
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

class NobelApi {

    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                }
            )
        }
    }

    suspend fun getPrizes(): NobelResponseDto {
        return client.get(
            "https://api.nobelprize.org/2.1/nobelPrizes"
        ).body()
    }
}