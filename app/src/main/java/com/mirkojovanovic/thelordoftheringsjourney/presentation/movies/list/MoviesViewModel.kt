package com.mirkojovanovic.thelordoftheringsjourney.presentation.movies.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.mirkojovanovic.thelordoftheringsjourney.data.dto.MovieDocDto
import com.mirkojovanovic.thelordoftheringsjourney.data.dto.MoviesPageDto
import com.mirkojovanovic.thelordoftheringsjourney.domain.prefs_store.PrefsStore
import com.mirkojovanovic.thelordoftheringsjourney.domain.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val movieRepository: MovieRepository
) : ViewModel() {

    private val _movies = MutableStateFlow<PagingData<MovieDocDto>?>(null)
    val movies: Flow<PagingData<MovieDocDto>?> by this::_movies

    init {
        loadMovies()
    }

    fun loadMovies() {
        viewModelScope.launch {
            _movies.emitAll(
                movieRepository.getMoviesPage().cachedIn(viewModelScope)
            )
        }
    }
}