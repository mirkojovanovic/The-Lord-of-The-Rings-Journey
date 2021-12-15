package com.mirkojovanovic.thelordoftheringsjourney.presentation.movies.detail.quotes

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
class MovieQuotesViewModel @Inject constructor(
) : ViewModel() {

}