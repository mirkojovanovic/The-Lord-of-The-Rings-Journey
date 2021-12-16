package com.mirkojovanovic.thelordoftheringsjourney.domain.use_case.movies

import androidx.paging.map
import com.mirkojovanovic.thelordoftheringsjourney.data.dto.quote.toQuoteDoc
import com.mirkojovanovic.thelordoftheringsjourney.domain.repository.MovieRepository
import com.mirkojovanovic.thelordoftheringsjourney.domain.use_case.characters.GetCharacterUseCase
import kotlinx.coroutines.flow.lastOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetMovieQuotesUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
    private val getCharacterUseCase: GetCharacterUseCase,
    private val getMovieUseCase: GetMovieUseCase,
) {

    suspend operator fun invoke(movieId: String) =
        movieRepository.getMovieQuotesPage(movieId)
            .map { pagingData ->
                pagingData.map { quoteDocDto ->
                    quoteDocDto.toQuoteDoc().copy(
                        character = getCharacterUseCase(quoteDocDto.character)
                            .lastOrNull()?.data?.name ?: "",
                        movie = getMovieUseCase(quoteDocDto.movie)
                            .lastOrNull()?.data?.name ?: ""
                    )
                }
            }
}