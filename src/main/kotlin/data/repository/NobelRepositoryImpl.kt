package com.example.data.repository

import com.example.data.db.table.LaureatesTable
import com.example.data.db.table.PrizesTable
import com.example.data.model.Laureate
import com.example.data.model.Prize
import com.example.domain.repository.NobelRepository
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class NobelRepositoryImpl : NobelRepository {

    private val prizes = listOf(

        Prize(
            year = "2023",
            category = "Physics",
            laureates = listOf(
                Laureate(
                    "1",
                    "Pierre Agostini",
                    "Experimental methods...",
                    "France"
                )
            )
        ),

        Prize(
            year = "2023",
            category = "Chemistry",
            laureates = listOf(
                Laureate(
                    "2",
                    "Moungi Bawendi",
                    "Quantum dots",
                    "France"
                )
            )
        )
    )

    override fun getPrizes(): List<Prize> = transaction {

        val prizes = PrizesTable.selectAll().map { row ->
            val prizeId = row[PrizesTable.id]

            val laureates = LaureatesTable
                .select { LaureatesTable.prizeId eq prizeId }
                .map {
                    Laureate(
                        id = it[LaureatesTable.id].toString(),
                        fullName = it[LaureatesTable.fullName],
                        motivation = it[LaureatesTable.motivation],
                        country = it[LaureatesTable.portraitUrl] ?: ""
                    )
                }

            Prize(
                year = row[PrizesTable.awardYear],
                category = row[PrizesTable.category],
                laureates = laureates
            )
        }

        prizes
    }

    override fun getPrize(
        year: String,
        category: String
    ): Prize? {
        return prizes.find {
            it.year == year &&
                    it.category.equals(
                        category,
                        true
                    )
        }
    }

    override fun getLaureates(
        year: String,
        category: String
    ): List<Laureate> {
        return getPrize(
            year,
            category
        )?.laureates ?: emptyList()
    }
}