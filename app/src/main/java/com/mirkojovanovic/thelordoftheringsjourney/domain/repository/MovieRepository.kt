package com.mirkojovanovic.thelordoftheringsjourney.domain.repository

import androidx.paging.PagingData
import com.mirkojovanovic.thelordoftheringsjourney.common.Resource
import com.mirkojovanovic.thelordoftheringsjourney.data.dto.MovieDocDto
import com.mirkojovanovic.thelordoftheringsjourney.data.dto.MoviesPageDto
import com.mirkojovanovic.thelordoftheringsjourney.data.dto.QuoteDocDto
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    suspend fun getMovies(): Flow<Resource<MoviesPageDto>>
    suspend fun getMoviesPage(sortType: String): Flow<PagingData<MovieDocDto>>
    suspend fun getMovieQuotesPage(movieId: String): Flow<PagingData<QuoteDocDto>>
}