package com.mirkojovanovic.thelordoftheringsjourney.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.mirkojovanovic.thelordoftheringsjourney.data.dto.MoviesPageDto
import com.mirkojovanovic.thelordoftheringsjourney.data.remote.RemotePagingSource
import com.mirkojovanovic.thelordoftheringsjourney.data.remote.TheOneApi
import com.mirkojovanovic.thelordoftheringsjourney.domain.repository.MovieRepository
import javax.inject.Inject


class MovieRepositoryImpl @Inject constructor(
    private val theOneApi: TheOneApi,
) : MovieRepository {

    override suspend fun getMovies(): MoviesPageDto = theOneApi.getMovies()

    override suspend fun getMoviesPage() = Pager(
        config = PagingConfig(pageSize = NETWORK_PAGE_SIZE),
        pagingSourceFactory = {
            RemotePagingSource { page ->
                theOneApi.getMovies(page)
            }
        }
    ).flow

    companion object {
        const val NETWORK_PAGE_SIZE = 15
    }

}

