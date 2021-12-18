package com.mirkojovanovic.thelordoftheringsjourney.domain.use_case.quotes

import com.mirkojovanovic.thelordoftheringsjourney.common.Resource
import com.mirkojovanovic.thelordoftheringsjourney.domain.model.quote.QuoteDoc
import com.mirkojovanovic.thelordoftheringsjourney.domain.repository.QuoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class GetAllQuotesUseCase @Inject constructor(
    private val quoteRepository: QuoteRepository,
) {

    suspend operator fun invoke(): Flow<Resource<List<QuoteDoc>>> = flow {
        quoteRepository.getQuotes().onEach { resource ->
            when (resource) {
                is Resource.Success -> {
                    emit(Resource.Success(resource.data))
                }
                is Resource.Error -> {
                    emit(Resource.Error(resource.message, resource.data))
                }
                is Resource.Loading -> {
                    emit(Resource.Loading())
                }
            }
        }
    }
}