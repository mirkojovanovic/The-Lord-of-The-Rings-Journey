package com.mirkojovanovic.thelordoftheringsjourney.data.repository

import com.mirkojovanovic.thelordoftheringsjourney.R
import com.mirkojovanovic.thelordoftheringsjourney.common.Resource
import com.mirkojovanovic.thelordoftheringsjourney.common.util.UiText
import com.mirkojovanovic.thelordoftheringsjourney.data.dto.quote.toQuoteDoc
import com.mirkojovanovic.thelordoftheringsjourney.data.remote.TheOneApi
import com.mirkojovanovic.thelordoftheringsjourney.domain.model.quote.QuoteDoc
import com.mirkojovanovic.thelordoftheringsjourney.domain.repository.QuoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


class QuoteRepositoryImpl @Inject constructor(
    private val theOneApi: TheOneApi,
) : QuoteRepository {

    override suspend fun getQuotes(): Flow<Resource<List<QuoteDoc>>> = flow {
        try {
            emit(Resource.Loading())
            val result = theOneApi.getQuotes().docs.map { it.toQuoteDoc() }
            emit(Resource.Success(result))
        } catch (e: HttpException) {
            emit(Resource.Error(UiText.StringResource(R.string.error_couldnt_load_all_quotes)))
        } catch (r: IOException) {
            emit(Resource.Error(UiText.unknownError()))
        }
    }

    override suspend fun getMovieQuotes(movieId: String): Flow<Resource<List<QuoteDoc>>> = flow {
        try {
            emit(Resource.Loading())
            val result = theOneApi.getMovieQuotes(movieId).docs.map { it.toQuoteDoc() }
            emit(Resource.Success(result))
        } catch (e: HttpException) {
            emit(Resource.Error(UiText.StringResource(R.string.error_couldnt_load_all_quotes)))
        } catch (r: IOException) {
            emit(Resource.Error(UiText.unknownError()))
        }
    }
}