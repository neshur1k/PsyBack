package com.example

import com.example.data.api.NobelApi
import com.example.data.db.DatabaseFactory
import com.example.data.service.PrizeImportService
import com.example.plugins.configureMonitoring
import io.ktor.server.application.*
import com.example.plugins.configureRouting
import com.example.plugins.configureSecurity
import com.example.plugins.configureSerialization
import kotlinx.coroutines.runBlocking

fun Application.module() {
    DatabaseFactory.init()

    runBlocking {

        val api = NobelApi()

        val response = api.getPrizes()

        println(
            "Loaded prizes: ${response.nobelPrizes.size}"
        )
    }

    configureSerialization()
    configureMonitoring()
    configureSecurity()
    configureRouting()
}