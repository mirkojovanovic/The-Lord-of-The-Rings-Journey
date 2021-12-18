package com.mirkojovanovic.thelordoftheringsjourney.domain.repository

import com.mirkojovanovic.thelordoftheringsjourney.common.Resource
import com.mirkojovanovic.thelordoftheringsjourney.domain.model.quote.QuoteDoc
import kotlinx.coroutines.flow.Flow

interface QuoteRepository {

    suspend fun getQuotes(): Flow<Resource<List<QuoteDoc>>>

    suspend fun getMovieQuotes(movieId: String): Flow<Resource<List<QuoteDoc>>>
}