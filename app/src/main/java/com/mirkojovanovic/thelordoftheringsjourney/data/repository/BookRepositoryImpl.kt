package com.mirkojovanovic.thelordoftheringsjourney.data.repository

import com.mirkojovanovic.thelordoftheringsjourney.data.dto.BooksPageDto
import com.mirkojovanovic.thelordoftheringsjourney.data.remote.TheOneApi
import com.mirkojovanovic.thelordoftheringsjourney.domain.repository.BookRepository
import javax.inject.Inject


class BookRepositoryImpl @Inject constructor(
    private val theOneApi: TheOneApi
) : BookRepository {

    override suspend fun getBooks(): BooksPageDto = theOneApi.getBooks()
}