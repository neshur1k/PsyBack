package com.example.data.service

import com.example.data.api.NobelApi
import com.example.data.db.table.LaureatesTable
import com.example.data.db.table.PrizesTable
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class PrizeImportService(
    private val api: NobelApi
) {

    suspend fun importPrizes() {

        val response = api.getPrizes()

        transaction {

            response.nobelPrizes.forEach { prize ->

                val exists =
                    PrizesTable
                        .selectAll()
                        .where {
                            (PrizesTable.awardYear eq prize.awardYear) and
                                    (PrizesTable.category eq prize.category.en)
                        }
                        .count() > 0

                if (exists) return@forEach

                val prizeId =
                    PrizesTable.insert {

                        it[awardYear] =
                            prize.awardYear

                        it[category] =
                            prize.category.en

                        it[fullName] =
                            prize.category.en

                        it[motivation] =
                            ""

                        it[detailLink] =
                            ""

                    } get PrizesTable.id

                prize.laureates.orEmpty().forEach { laureate ->

                    LaureatesTable.insert {

                        it[this.prizeId] = prizeId

                        it[fullName] =
                            laureate.fullName?.en ?: ""

                        it[portion] =
                            laureate.portion ?: ""

                        it[motivation] =
                            laureate.motivation?.en ?: ""

                        it[portraitUrl] =
                            laureate.portraitUrl
                    }
                }
            }
        }

        println("IMPORT FINISHED")
    }
}