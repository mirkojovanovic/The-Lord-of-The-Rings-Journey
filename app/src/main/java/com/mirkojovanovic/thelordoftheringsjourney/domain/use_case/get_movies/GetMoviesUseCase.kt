package com.mirkojovanovic.thelordoftheringsjourney.domain.use_case.get_movies

import com.mirkojovanovic.thelordoftheringsjourney.common.Resource
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
    operator fun invoke(): Flow<Resource<MoviesPage>> = flow {
        try {
            emit(Resource.Loading())
            val movies = repository.getMovies().toMoviesPage()
            emit(Resource.Success(movies))
        } catch (e: HttpException) {
            emit(Resource.Error(e.message ?: "Unexpected error"))
        } catch (e: IOException) {
            emit(Resource.Error(e.message ?: "Server unreachable"))
        }
    }
}