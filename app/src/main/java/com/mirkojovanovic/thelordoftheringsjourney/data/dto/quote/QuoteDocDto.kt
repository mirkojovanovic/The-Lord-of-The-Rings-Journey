package com.mirkojovanovic.thelordoftheringsjourney.data.dto.quote

import com.mirkojovanovic.thelordoftheringsjourney.domain.model.quote.QuoteDoc


data class QuoteDocDto(
    val _id: String,
    val character: String,
    val dialog: String,
    val movie: String,
)

fun QuoteDocDto.toQuoteDoc() =
    QuoteDoc(
        _id = _id,
        character = character,
        dialog = dialog,
        movie = movie
    )
