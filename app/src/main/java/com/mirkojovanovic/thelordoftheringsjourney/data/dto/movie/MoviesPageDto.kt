package com.mirkojovanovic.thelordoftheringsjourney.data.dto.movie

import com.mirkojovanovic.thelordoftheringsjourney.domain.model.movie.MoviesPage

data class MoviesPageDto(
    val docs: List<MovieDocDto>,
    val limit: Int,
    val offset: Int,
    val page: Int,
    val pages: Int,
    val total: Int,
)

fun MoviesPageDto.toMovie() =
    docs[0].toMovieDoc()


fun MoviesPageDto.toMoviesPage() =
    MoviesPage(
        docs = docs.map { it.toMovieDoc() },
        total = total
    )