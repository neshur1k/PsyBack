package com.example.data.repository

import com.example.data.model.Laureate
import com.example.data.model.Prize
import com.example.domain.repository.NobelRepository

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

    override fun getPrizes() = prizes

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