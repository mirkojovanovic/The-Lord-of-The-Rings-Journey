package com.mirkojovanovic.thelordoftheringsjourney.presentation.movies.info.quotes

import com.mirkojovanovic.thelordoftheringsjourney.common.util.UiText
import com.mirkojovanovic.thelordoftheringsjourney.domain.model.character.Character
import com.mirkojovanovic.thelordoftheringsjourney.domain.model.movie.MovieDoc
import com.mirkojovanovic.thelordoftheringsjourney.domain.model.quote.QuoteDoc

data class MovieQuotesState(
    val tab: InfoTab = InfoTab.Details,
    val movie: MovieDoc? = null,
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val message: UiText? = null,
    val query: String? = null,
    val quotes: List<QuoteDoc>? = null,
    val characters: List<Character>? = null,
)

sealed class InfoTab {
    object Details : InfoTab()
    object Quotes : InfoTab()
}