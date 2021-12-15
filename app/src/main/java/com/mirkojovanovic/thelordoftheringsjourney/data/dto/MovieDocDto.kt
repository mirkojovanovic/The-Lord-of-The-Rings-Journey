package com.mirkojovanovic.thelordoftheringsjourney.data.dto

import com.mirkojovanovic.thelordoftheringsjourney.domain.model.MovieDoc

data class MovieDocDto(
    val _id: String,
    val academyAwardNominations: Int,
    val academyAwardWins: Int,
    val boxOfficeRevenueInMillions: Float,
    val budgetInMillions: Int,
    val name: String,
    val rottenTomatoesScore: Float,
    val runtimeInMinutes: Int,
)

fun MovieDocDto.toMovieDoc() =
    MovieDoc(
        _id = _id,
        academyAwardNominations = academyAwardNominations,
        academyAwardWins = academyAwardWins,
        boxOfficeRevenueInMillions = boxOfficeRevenueInMillions,
        budgetInMillions = budgetInMillions,
        name = name,
        rottenTomatoesScore = rottenTomatoesScore,
        runtimeInMinutes = runtimeInMinutes
    )