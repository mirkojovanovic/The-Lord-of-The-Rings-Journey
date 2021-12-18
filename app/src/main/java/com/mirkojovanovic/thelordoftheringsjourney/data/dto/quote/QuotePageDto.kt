package com.mirkojovanovic.thelordoftheringsjourney.data.dto.quote

import com.mirkojovanovic.thelordoftheringsjourney.domain.model.quote.QuoteDoc

data class QuotePageDto(
    val docs: List<QuoteDocDto>,
    val limit: Int,
    val offset: Int,
    val page: Int,
    val pages: Int,
    val total: Int
)