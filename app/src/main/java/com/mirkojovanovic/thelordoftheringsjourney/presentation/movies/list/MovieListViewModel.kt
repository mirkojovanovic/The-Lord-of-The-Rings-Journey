package com.mirkojovanovic.thelordoftheringsjourney.presentation.movies.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.mirkojovanovic.thelordoftheringsjourney.data.dto.MovieDocDto
import com.mirkojovanovic.thelordoftheringsjourney.domain.use_case.movies.GetMoviesPageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val getMoviesPageUseCase: GetMoviesPageUseCase,
) : ViewModel() {

    private val _movies = MutableStateFlow<PagingData<MovieDocDto>?>(null)
    val movies: Flow<PagingData<MovieDocDto>?> by this::_movies

    private val _sortType =
        MutableStateFlow<GetMoviesPageUseCase.SortType>(GetMoviesPageUseCase.SortType.Unsorted)
    val sortType: Flow<GetMoviesPageUseCase.SortType> by this::_sortType

    private val _sortBy =
        MutableStateFlow<GetMoviesPageUseCase.SortBy?>(null)
    val sortBy: Flow<GetMoviesPageUseCase.SortBy?> by this::_sortBy


    fun loadMovies() {
        viewModelScope.launch {
            _movies.emitAll(
                getMoviesPageUseCase(sortType.first(), sortBy.first()).cachedIn(viewModelScope)
            )
        }
    }

    fun setSortType(type: GetMoviesPageUseCase.SortType) {
        viewModelScope.launch {
            _sortType.emit(type)
        }
    }

    fun setSortBy(by: GetMoviesPageUseCase.SortBy?) {
        viewModelScope.launch {
            _sortBy.emit(by)
        }
    }


}