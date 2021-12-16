package com.mirkojovanovic.thelordoftheringsjourney.data.dto


data class PagedResponseDto<T>(
    val docs: List<T>? = null,
    val limit: Int,
    val offset: Int,
    val page: Int,
    val pages: Int,
    val total: Int,
)