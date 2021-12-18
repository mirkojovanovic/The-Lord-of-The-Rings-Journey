package com.mirkojovanovic.thelordoftheringsjourney.presentation.movies.info.quotes

import com.mirkojovanovic.thelordoftheringsjourney.common.util.UiText
import com.mirkojovanovic.thelordoftheringsjourney.domain.model.character.Character
import com.mirkojovanovic.thelordoftheringsjourney.domain.model.movie.MovieDoc
import com.mirkojovanovic.thelordoftheringsjourney.domain.model.quote.QuoteDoc

data class MovieQuotesState(
    val movie: MovieDoc? = null,
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val message: UiText? = null,
    val query: String? = null,
    val quotes: List<QuoteDoc>? = listOf(),
    val characters: List<Character>? = listOf()
)