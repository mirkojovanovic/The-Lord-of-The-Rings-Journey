package com.mirkojovanovic.thelordoftheringsjourney.domain.model

data class BooksPage(
    val docs: List<BookDoc>,
    val total: Int,
)