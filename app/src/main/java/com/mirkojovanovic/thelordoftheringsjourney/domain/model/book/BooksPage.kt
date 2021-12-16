package com.mirkojovanovic.thelordoftheringsjourney.domain.model.book

data class BooksPage(
    val docs: List<BookDoc>,
    val total: Int,
)