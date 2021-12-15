package com.mirkojovanovic.thelordoftheringsjourney.domain.use_case.get_books

import com.mirkojovanovic.thelordoftheringsjourney.common.Resource
import com.mirkojovanovic.thelordoftheringsjourney.data.dto.toBooksPage
import com.mirkojovanovic.thelordoftheringsjourney.domain.model.BooksPage
import com.mirkojovanovic.thelordoftheringsjourney.domain.repository.BookRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetBooksUseCase @Inject constructor(
    private val repository: BookRepository
) {
    operator fun invoke(): Flow<Resource<BooksPage>> = flow {
        try {
            emit(Resource.Loading())
            val books = repository.getBooks().toBooksPage()
            emit(Resource.Success(books))
        } catch (e: HttpException) {
            emit(Resource.Error(e.message ?: "Unexpected error"))
        } catch (e: IOException) {
            emit(Resource.Error(e.message ?: "Server unreachable"))
        }
    }
}