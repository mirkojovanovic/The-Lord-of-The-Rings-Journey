package com.mirkojovanovic.thelordoftheringsjourney.data.dto

import com.mirkojovanovic.thelordoftheringsjourney.domain.model.BooksPage

data class BooksPageDto(
    val docs: List<BookDocDto>,
    val limit: Int,
    val offset: Int,
    val page: Int,
    val pages: Int,
    val total: Int,
)

fun BooksPageDto.toBooksPage() =
    BooksPage(
        docs = docs.map { it.toBookDoc() },
        total = total
    )