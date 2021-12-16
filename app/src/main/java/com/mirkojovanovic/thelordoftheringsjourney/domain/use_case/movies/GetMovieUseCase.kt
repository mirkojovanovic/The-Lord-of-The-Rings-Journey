package com.mirkojovanovic.thelordoftheringsjourney.domain.use_case.movies

import com.mirkojovanovic.thelordoftheringsjourney.domain.repository.MovieRepository
import javax.inject.Inject


class GetMovieUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
) {

    suspend operator fun invoke(id: String) = movieRepository.getMovie(id)
}