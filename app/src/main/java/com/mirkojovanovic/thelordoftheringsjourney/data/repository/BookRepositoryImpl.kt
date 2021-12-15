package com.mirkojovanovic.thelordoftheringsjourney.data.repository

import com.mirkojovanovic.thelordoftheringsjourney.common.Resource
import com.mirkojovanovic.thelordoftheringsjourney.data.dto.BooksPageDto
import com.mirkojovanovic.thelordoftheringsjourney.data.remote.TheOneApi
import com.mirkojovanovic.thelordoftheringsjourney.domain.repository.BookRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


class BookRepositoryImpl @Inject constructor(
    private val theOneApi: TheOneApi
) : BookRepository {

    override suspend fun getBooks(): Flow<Resource<BooksPageDto>> = flow {
        try {
            emit(Resource.Loading())
            val books = theOneApi.getBooks()
            emit(Resource.Success(books))
        } catch (e: HttpException) {
            emit(Resource.Error(e.message))
        } catch (e: IOException) {
            emit(Resource.Error(e.message))
        }
    }
}