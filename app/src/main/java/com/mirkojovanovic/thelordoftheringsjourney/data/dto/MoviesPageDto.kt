package com.mirkojovanovic.thelordoftheringsjourney.data.dto

import com.mirkojovanovic.thelordoftheringsjourney.domain.model.MoviesPage

data class MoviesPageDto(
    val docs: List<MovieDocDto>,
    val limit: Int,
    val offset: Int,
    val page: Int,
    val pages: Int,
    val total: Int,
)

fun MoviesPageDto.toMoviesPage() =
    MoviesPage(
        docs = docs.map { it.toMovieDoc() },
        total = total
    )