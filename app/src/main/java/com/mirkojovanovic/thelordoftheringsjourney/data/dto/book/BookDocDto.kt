package com.mirkojovanovic.thelordoftheringsjourney.data.dto.book

import com.mirkojovanovic.thelordoftheringsjourney.domain.model.book.BookDoc

data class BookDocDto(
    val _id: String,
    val name: String,
)

fun BookDocDto.toBookDoc() =
    BookDoc(
        _id = _id,
        name = name
    )