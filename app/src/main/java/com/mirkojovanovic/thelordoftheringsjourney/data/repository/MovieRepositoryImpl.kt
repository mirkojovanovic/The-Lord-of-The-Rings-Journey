package com.mirkojovanovic.thelordoftheringsjourney.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.mirkojovanovic.thelordoftheringsjourney.common.Resource
import com.mirkojovanovic.thelordoftheringsjourney.data.dto.movie.MoviesPageDto
import com.mirkojovanovic.thelordoftheringsjourney.data.dto.movie.toMovieDoc
import com.mirkojovanovic.thelordoftheringsjourney.data.remote.RemotePagingSource
import com.mirkojovanovic.thelordoftheringsjourney.data.remote.TheOneApi
import com.mirkojovanovic.thelordoftheringsjourney.domain.model.movie.MovieDoc
import com.mirkojovanovic.thelordoftheringsjourney.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


class MovieRepositoryImpl @Inject constructor(
    private val theOneApi: TheOneApi,
) : MovieRepository {


    override suspend fun getMovie(id: String): Flow<Resource<MovieDoc>> = flow {
        try {
            emit(Resource.Loading())
            val movie = theOneApi.getMovie(id)
            emit(Resource.Success(movie.docs[0].toMovieDoc()))
        } catch (e: HttpException) {
            emit(Resource.Error(e.message))
        } catch (e: IOException) {
            emit(Resource.Error(e.message))
        }
    }

    override suspend fun getMovies(): Flow<Resource<MoviesPageDto>> = flow {
        try {
            emit(Resource.Loading())
            val movies = theOneApi.getMovies()
            emit(Resource.Success(movies))
        } catch (e: HttpException) {
            emit(Resource.Error(e.message))
        } catch (e: IOException) {
            emit(Resource.Error(e.message))
        }
    }

    override suspend fun getMoviesPage(sortType: String) = Pager(
        config = PagingConfig(pageSize = NETWORK_PAGE_SIZE),
        pagingSourceFactory = {
            RemotePagingSource { page ->
                theOneApi.getMovies(page, NETWORK_PAGE_SIZE, sortType)
            }
        }
    ).flow

    override suspend fun getMovieQuotesPage(movieId: String) = Pager(
        config = PagingConfig(pageSize = NETWORK_PAGE_SIZE),
        pagingSourceFactory = {
            RemotePagingSource { page ->
                theOneApi.getMovieQuotes(movieId, page, NETWORK_PAGE_SIZE)
            }
        }
    ).flow


    companion object {
        const val NETWORK_PAGE_SIZE = 5
    }

}

