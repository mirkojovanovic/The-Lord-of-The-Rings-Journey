package com.mirkojovanovic.thelordoftheringsjourney.presentation.home

data class BooksMoviesCountState(
    val bookCount: Int? = null,
    val movieCount: Int? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
)