package com.mirkojovanovic.thelordoftheringsjourney.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mirkojovanovic.thelordoftheringsjourney.common.Resource
import com.mirkojovanovic.thelordoftheringsjourney.domain.use_case.books.GetBooksUseCase
import com.mirkojovanovic.thelordoftheringsjourney.domain.use_case.movies.GetMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getBooksUseCase: GetBooksUseCase,
    private val getMoviesUseCase: GetMoviesUseCase,
) : ViewModel() {

    private val _countState = MutableStateFlow(BooksMoviesCountState())
    val countState: Flow<BooksMoviesCountState> by this::_countState

    init {
        getBookCount()
        getMovieCount()
    }

    private fun getBookCount() {
        viewModelScope.launch {
            getBooksUseCase().onEach { result ->
                with(_countState) {
                    when (result) {
                        is Resource.Success -> {
                            emit(first().copy(bookCount = result.data?.total))
                        }
                        is Resource.Error -> {
                            emit(first().copy(error = result.message))
                        }
                        is Resource.Loading -> {
                            emit(first().copy(isLoading = true))
                        }
                    }
                }
            }.launchIn(viewModelScope)
        }

    }

    private fun getMovieCount() {
        viewModelScope.launch {
            getMoviesUseCase().onEach { result ->
                with(_countState) {
                    when (result) {
                        is Resource.Success -> {
                            emit(first().copy(movieCount = result.data?.total))
                        }
                        is Resource.Error -> {
                            emit(first().copy(error = result.message))
                        }
                        is Resource.Loading -> {
                            emit(first().copy(isLoading = true))
                        }
                    }
                }

            }.launchIn(viewModelScope)
        }
    }

}