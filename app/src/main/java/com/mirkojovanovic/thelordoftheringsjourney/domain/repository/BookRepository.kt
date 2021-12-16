package com.mirkojovanovic.thelordoftheringsjourney.domain.repository

import com.mirkojovanovic.thelordoftheringsjourney.common.Resource
import com.mirkojovanovic.thelordoftheringsjourney.data.dto.book.BooksPageDto
import kotlinx.coroutines.flow.Flow

interface BookRepository {

    suspend fun getBooks(): Flow<Resource<BooksPageDto>>
}