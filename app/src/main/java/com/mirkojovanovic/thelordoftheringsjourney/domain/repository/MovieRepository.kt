package com.mirkojovanovic.thelordoftheringsjourney.domain.repository

import androidx.paging.PagingData
import com.mirkojovanovic.thelordoftheringsjourney.common.Resource
import com.mirkojovanovic.thelordoftheringsjourney.data.dto.movie.MovieDocDto
import com.mirkojovanovic.thelordoftheringsjourney.data.dto.movie.MoviesPageDto
import com.mirkojovanovic.thelordoftheringsjourney.data.dto.quote.QuoteDocDto
import com.mirkojovanovic.thelordoftheringsjourney.domain.model.movie.MovieDoc
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    suspend fun getMovie(id: String): Flow<Resource<MovieDoc>>
    suspend fun getMovies(): Flow<Resource<MoviesPageDto>>
    suspend fun getMoviesPage(sortType: String): Flow<PagingData<MovieDocDto>>
    suspend fun getMovieQuotesPage(movieId: String): Flow<PagingData<QuoteDocDto>>
}