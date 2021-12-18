package com.mirkojovanovic.thelordoftheringsjourney.presentation.home

import com.mirkojovanovic.thelordoftheringsjourney.common.util.UiText

data class BooksMoviesCountState(
    val bookCount: Int? = null,
    val movieCount: Int? = null,
    val isLoading: Boolean = false,
    val error: UiText? = null,
)