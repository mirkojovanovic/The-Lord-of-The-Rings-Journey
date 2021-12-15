package com.mirkojovanovic.thelordoftheringsjourney.data.remote

import com.mirkojovanovic.thelordoftheringsjourney.data.dto.BooksPageDto
import com.mirkojovanovic.thelordoftheringsjourney.data.dto.MovieDocDto
import com.mirkojovanovic.thelordoftheringsjourney.data.dto.MoviesPageDto
import com.mirkojovanovic.thelordoftheringsjourney.data.dto.PagedResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TheOneApi {

    @GET("book")
    suspend fun getBooks(): BooksPageDto

    @GET("movie")
    suspend fun getMovies(): MoviesPageDto

    @GET("movie")
    suspend fun getMovies(@Query("page") page: Int): Response<PagedResponseDto<MovieDocDto>>
}