package com.mirkojovanovic.thelordoftheringsjourney.presentation.movies.info

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mirkojovanovic.thelordoftheringsjourney.common.Resource
import com.mirkojovanovic.thelordoftheringsjourney.common.util.UiText
import com.mirkojovanovic.thelordoftheringsjourney.domain.model.movie.MovieDoc
import com.mirkojovanovic.thelordoftheringsjourney.domain.use_case.characters.GetAllCharactersUseCase
import com.mirkojovanovic.thelordoftheringsjourney.domain.use_case.quotes.GetMovieQuotesUseCase
import com.mirkojovanovic.thelordoftheringsjourney.presentation.movies.info.quotes.MovieQuotesState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class MovieInfoViewModel @Inject constructor(
    private val getAllCharactersUseCase: GetAllCharactersUseCase,
    private val getMovieQuotesUseCase: GetMovieQuotesUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(MovieQuotesState())
    val state: Flow<MovieQuotesState> = _state.asStateFlow()

    private val _event = MutableSharedFlow<UIEvent>()
    val event = _event.asSharedFlow()

    fun setStateMovie(movie: MovieDoc) {
        _state.value = _state.value.copy(
            movie = movie
        )
        getMovieQuotes()
    }

    fun setQuery(query: String) {
        _state.value = _state.value.copy(
            query = query
        )
    }

    fun getCharacters() {
        viewModelScope.launch {
            getAllCharactersUseCase().onEach { characters ->
                when (characters) {
                    is Resource.Success -> {
                        _state.emit(
                            _state.value.copy(
                                characters = characters.data,
                                isError = false,
                                isLoading = false
                            )
                        )
                        characters.data?.let {
                            _state.emit(_state.value.copy(characters = it))
                        }
                        _event.emit(UIEvent.HideLoadingAnimation)
                    }
                    is Resource.Error -> {
                        _state.emit(
                            _state.value.copy(
                                isError = true,
                                message = characters.message
                            )
                        )
                        _event.emit(UIEvent.HideLoadingAnimation)
                        delay(50)
                        _event.emit(UIEvent.ShowSnackBar(characters.message))
                    }
                    is Resource.Loading -> {
                        _state.emit(
                            _state.value.copy(
                                isLoading = true,
                                isError = false,
                                message = characters.message,
                            )
                        )
                        _event.emit(UIEvent.ShowLoadingAnimation(characters.message))
                    }
                }
            }.flowOn(Dispatchers.IO).launchIn(viewModelScope)
        }
    }

    private fun getMovieQuotes() {
        viewModelScope.launch {
            _state.value.movie?._id?.let {
                getMovieQuotesUseCase(it).onEach { quotes ->
                    when (quotes) {
                        is Resource.Success -> {
                            Timber.d("quotes are served ${quotes.data}")
                            quotes.data?.let { data ->
                                _state.emit(
                                    _state.value.copy(
                                        isError = false,
                                        isLoading = false,
                                        quotes = data
                                    ))
                            }
                            _event.emit(UIEvent.HideLoadingAnimation)
                        }
                        is Resource.Error -> {
                            _state.emit(
                                _state.value.copy(
                                    isError = true,
                                    isLoading = false,
                                ))
                            _event.emit(UIEvent.ShowSnackBar(quotes.message))
                            _event.emit(UIEvent.HideLoadingAnimation)
                        }
                        is Resource.Loading -> {
                            _state.emit(
                                _state.value.copy(
                                    isError = false,
                                    isLoading = true,
                                ))
                            _event.emit(UIEvent.ShowLoadingAnimation(quotes.message))
                        }
                    }
                }.flowOn(Dispatchers.IO).launchIn(viewModelScope)
            }
        }
    }

    sealed class UIEvent {
        data class ShowSnackBar(val message: UiText?) : UIEvent()
        data class ShowLoadingAnimation(val message: UiText?) : UIEvent()
        object HideLoadingAnimation : UIEvent()
    }

}
