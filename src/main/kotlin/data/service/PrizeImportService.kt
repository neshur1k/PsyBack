package com.example.data.service

import com.example.data.api.NobelApi

class PrizeImportService(
    private val api: NobelApi
) {

    suspend fun importPrizes() {

        val response = api.getPrizes()

        println(
            "Imported ${response.nobelPrizes.size} prizes"
        )

    }
}