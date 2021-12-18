package com.mirkojovanovic.thelordoftheringsjourney.domain.use_case.quotes

import com.mirkojovanovic.thelordoftheringsjourney.common.Resource
import com.mirkojovanovic.thelordoftheringsjourney.domain.model.quote.QuoteDoc
import com.mirkojovanovic.thelordoftheringsjourney.domain.repository.QuoteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMovieQuotesUseCase @Inject constructor(
    private val quoteRepository: QuoteRepository,
) {

    suspend operator fun invoke(movieId: String): Flow<Resource<List<QuoteDoc>>> =
        quoteRepository.getMovieQuotes(movieId)
}