package com.mirkojovanovic.thelordoftheringsjourney.presentation.movies.info.quotes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.mirkojovanovic.thelordoftheringsjourney.domain.model.quote.QuoteDoc
import com.mirkojovanovic.thelordoftheringsjourney.domain.use_case.movies.GetMovieQuotesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieQuotesViewModel @Inject constructor(
    private val getMovieQuotesUseCase: GetMovieQuotesUseCase,
) : ViewModel() {

    private val _movieQuotes = MutableStateFlow<PagingData<QuoteDoc>?>(null)
    val movieQuotes: Flow<PagingData<QuoteDoc>?> by this::_movieQuotes

    fun loadMovieQuotes(movieId: String) {
        viewModelScope.launch {
            _movieQuotes.emitAll(
                getMovieQuotesUseCase(movieId).cachedIn(viewModelScope)
            )
        }
    }
}
