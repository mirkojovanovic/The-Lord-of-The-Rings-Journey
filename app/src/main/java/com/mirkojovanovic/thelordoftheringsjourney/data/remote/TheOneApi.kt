package com.mirkojovanovic.thelordoftheringsjourney.data.remote

import com.mirkojovanovic.thelordoftheringsjourney.data.dto.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TheOneApi {

    @GET("book")
    suspend fun getBooks(): BooksPageDto

    @GET("movie")
    suspend fun getMovies(): MoviesPageDto

    @GET("movie")
    suspend fun getMovies(
        @Query("page") page: Int,
        @Query("limit") limit: Int,
        @Query("sort") sort: String
    ): Response<PagedResponseDto<MovieDocDto>>

    @GET("movie/{id}/quote")
    suspend fun getMovieQuotes(
        @Path("id") movieId: String,
        @Query("page") page: Int,
        @Query("limit") limit: Int,
    ): Response<PagedResponseDto<QuoteDocDto>>

}