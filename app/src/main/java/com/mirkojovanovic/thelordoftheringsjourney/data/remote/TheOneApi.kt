package com.mirkojovanovic.thelordoftheringsjourney.data.remote

import com.mirkojovanovic.thelordoftheringsjourney.data.dto.PagedResponseDto
import com.mirkojovanovic.thelordoftheringsjourney.data.dto.book.BooksPageDto
import com.mirkojovanovic.thelordoftheringsjourney.data.dto.character.CharacterPageDto
import com.mirkojovanovic.thelordoftheringsjourney.data.dto.movie.MovieDocDto
import com.mirkojovanovic.thelordoftheringsjourney.data.dto.movie.MoviesPageDto
import com.mirkojovanovic.thelordoftheringsjourney.data.dto.quote.QuoteDocDto
import com.mirkojovanovic.thelordoftheringsjourney.data.dto.quote.QuotePageDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TheOneApi {

    // Books

    @GET("book")
    suspend fun getBooks(): BooksPageDto

    // Movies

    @GET("movie/{id}")
    suspend fun getMovie(@Path("id") id: String): MoviesPageDto


    @GET("movie")
    suspend fun getMovies(): MoviesPageDto

    @GET("movie")
    suspend fun getMovies(
        @Query("page") page: Int,
        @Query("limit") limit: Int,
        @Query("sort") sort: String,
    ): Response<PagedResponseDto<MovieDocDto>>

    // Characters

    @GET("character/{id}")
    suspend fun getCharacter(
        @Path("id") id: String,
    ): CharacterPageDto

    @GET("character")
    suspend fun getCharacterByName(
        @Path("name") name: String,
    ): CharacterPageDto

    // Quotes

    @GET("quote")
    suspend fun getQuotes(): QuotePageDto

    @GET("movie/{id}/quote")
    suspend fun getMovieQuotesPage(
        @Path("id") movieId: String,
        @Query("page") page: Int,
        @Query("limit") limit: Int,
    ): Response<PagedResponseDto<QuoteDocDto>>

    @GET("movie/{movieId}/quote")
    suspend fun getMovieQuotes(
        @Path("movieId") movieId: String,
    ): QuotePageDto

    // Character

    @GET("character")
    suspend fun getCharacters(): CharacterPageDto

    @GET("character/{characterId}/quote")
    suspend fun getCharacterQuotesById(
        @Path("characterId") characterId: String,
    ): QuoteDocDto

}