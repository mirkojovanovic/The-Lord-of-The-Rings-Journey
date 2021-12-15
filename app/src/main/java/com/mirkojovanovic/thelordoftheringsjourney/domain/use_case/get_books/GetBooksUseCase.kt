package com.mirkojovanovic.thelordoftheringsjourney.domain.use_case.get_books

import com.mirkojovanovic.thelordoftheringsjourney.common.Resource
import com.mirkojovanovic.thelordoftheringsjourney.data.dto.BooksPageDto
import com.mirkojovanovic.thelordoftheringsjourney.domain.repository.BookRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBooksUseCase @Inject constructor(
    private val repository: BookRepository,
) {
    suspend operator fun invoke(): Flow<Resource<BooksPageDto>> = repository.getBooks()
}