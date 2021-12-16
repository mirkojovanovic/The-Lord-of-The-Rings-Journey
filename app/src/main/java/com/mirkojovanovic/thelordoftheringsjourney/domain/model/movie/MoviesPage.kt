package com.mirkojovanovic.thelordoftheringsjourney.domain.model.movie


data class MoviesPage(
    val docs: List<MovieDoc>,
    val total: Int,
)
