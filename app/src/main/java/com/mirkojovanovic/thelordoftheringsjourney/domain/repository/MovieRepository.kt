package com.mirkojovanovic.thelordoftheringsjourney.domain.repository

import androidx.paging.PagingData
import com.mirkojovanovic.thelordoftheringsjourney.data.dto.MovieDocDto
import com.mirkojovanovic.thelordoftheringsjourney.data.dto.MoviesPageDto
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    suspend fun getMovies(): MoviesPageDto
    suspend fun getMoviesPage(): Flow<PagingData<MovieDocDto>>
}