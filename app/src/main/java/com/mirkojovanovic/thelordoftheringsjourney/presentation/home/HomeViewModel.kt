package com.mirkojovanovic.thelordoftheringsjourney.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mirkojovanovic.thelordoftheringsjourney.common.Resource
import com.mirkojovanovic.thelordoftheringsjourney.common.util.UiText
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

    private val _event = MutableSharedFlow<UIEvent>()
    val event = _event.asSharedFlow()

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
                            emit(_countState.value.copy(bookCount = result.data?.total))
                            _event.emit(UIEvent.HideLoadingAnimation)
                        }
                        is Resource.Error -> {
                            emit(_countState.value.copy(error = result.message))
                            _event.emit(UIEvent.ShowSnackBar(result.message))
                            _event.emit(UIEvent.HideLoadingAnimation)
                        }
                        is Resource.Loading -> {
                            emit(_countState.value.copy(isLoading = true))
                            _event.emit(UIEvent.ShowLoadingAnimation(result.message))
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
                            emit(_countState.value.copy(movieCount = result.data?.total))
                            _event.emit(UIEvent.HideLoadingAnimation)
                        }
                        is Resource.Error -> {
                            emit(_countState.value.copy(error = result.message))
                            _event.emit(UIEvent.ShowSnackBar(result.message))
                            _event.emit(UIEvent.HideLoadingAnimation)
                        }
                        is Resource.Loading -> {
                            emit(_countState.value.copy(isLoading = true))
                            _event.emit(UIEvent.ShowLoadingAnimation(result.message))
                        }
                    }
                }

            }.launchIn(viewModelScope)
        }
    }

    sealed class UIEvent {
        data class ShowSnackBar(val message: UiText?) : UIEvent()
        data class ShowLoadingAnimation(val message: UiText?) : UIEvent()
        object HideLoadingAnimation : UIEvent()
    }
}