package com.mirkojovanovic.thelordoftheringsjourney.domain.use_case.movies

import com.mirkojovanovic.thelordoftheringsjourney.common.Resource
import com.mirkojovanovic.thelordoftheringsjourney.data.dto.movie.MoviesPageDto
import com.mirkojovanovic.thelordoftheringsjourney.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetMoviesUseCase @Inject constructor(
    private val repository: MovieRepository,
) {
    suspend operator fun invoke(): Flow<Resource<MoviesPageDto>> = repository.getMovies()
}