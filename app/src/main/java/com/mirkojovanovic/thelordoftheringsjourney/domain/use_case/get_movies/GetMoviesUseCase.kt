package com.mirkojovanovic.thelordoftheringsjourney.domain.use_case.get_movies

import com.mirkojovanovic.thelordoftheringsjourney.common.Resource
import com.mirkojovanovic.thelordoftheringsjourney.data.dto.MoviesPageDto
import com.mirkojovanovic.thelordoftheringsjourney.data.dto.toMoviesPage
import com.mirkojovanovic.thelordoftheringsjourney.domain.model.MoviesPage
import com.mirkojovanovic.thelordoftheringsjourney.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


class GetMoviesUseCase @Inject constructor(
    private val repository: MovieRepository,
) {
    suspend operator fun invoke(): Flow<Resource<MoviesPageDto>> = repository.getMovies()
}