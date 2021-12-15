package com.mirkojovanovic.thelordoftheringsjourney.domain.repository

import com.mirkojovanovic.thelordoftheringsjourney.data.dto.BooksPageDto

interface BookRepository {

    suspend fun getBooks(): BooksPageDto
}