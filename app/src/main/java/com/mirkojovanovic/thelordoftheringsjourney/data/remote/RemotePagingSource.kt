package com.mirkojovanovic.thelordoftheringsjourney.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mirkojovanovic.thelordoftheringsjourney.data.dto.PagedResponseDto
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

class RemotePagingSource<T : Any>(
    private val apiCall: suspend (page: Int) -> Response<PagedResponseDto<T>>,
) : PagingSource<Int, T>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        val position = params.key ?: STARTING_PAGE_INDEX
        return try {
            val response = apiCall(position)
            val meta = response.body()
            val data = response.body()?.docs

            val nextPageKey = if (meta?.pages != meta?.page) meta?.page?.plus(1) else null

            LoadResult.Page(
                data = data ?: emptyList(),
                prevKey = null,
                nextKey = nextPageKey,
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, T>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    companion object {
        private const val STARTING_PAGE_INDEX = 1
    }
}
