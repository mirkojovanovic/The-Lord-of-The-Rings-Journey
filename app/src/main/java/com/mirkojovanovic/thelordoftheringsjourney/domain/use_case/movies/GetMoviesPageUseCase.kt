package com.mirkojovanovic.thelordoftheringsjourney.domain.use_case.movies

import androidx.paging.PagingData
import com.mirkojovanovic.thelordoftheringsjourney.data.dto.movie.MovieDocDto
import com.mirkojovanovic.thelordoftheringsjourney.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

private const val SORT_MOVIES_BY_SCORE = "rottenTomatoesScore"
private const val SORT_MOVIES_BY_RUNTIME = "runtimeInMinutes"
private const val SORT_MOVIES_ASCENDING = "asc"
private const val SORT_MOVIES_DESCENDING = "desc"
private const val SORT_MOVIES_UNSORTED = ""

class GetMoviesPageUseCase @Inject constructor(
    private val repository: MovieRepository,
) {
    suspend operator fun invoke(
        sortType: SortType,
        sortBy: SortBy?,
    ): Flow<PagingData<MovieDocDto>> =
        flow {
            val sortTypeString = when (sortBy) {
                is SortBy.Score -> {
                    when (sortType) {
                        is SortType.Unsorted -> SORT_MOVIES_UNSORTED
                        is SortType.Ascending -> "$SORT_MOVIES_BY_SCORE:$SORT_MOVIES_ASCENDING"
                        is SortType.Descending -> "$SORT_MOVIES_BY_SCORE:$SORT_MOVIES_DESCENDING"
                    }
                }
                is SortBy.Runtime -> {
                    when (sortType) {
                        is SortType.Unsorted -> SORT_MOVIES_UNSORTED
                        is SortType.Ascending -> "$SORT_MOVIES_BY_RUNTIME:$SORT_MOVIES_ASCENDING"
                        is SortType.Descending -> "$SORT_MOVIES_BY_RUNTIME:$SORT_MOVIES_DESCENDING"
                    }
                }
                else -> SORT_MOVIES_UNSORTED
            }
            emit(repository.getMoviesPage(sortTypeString).first())
        }


    sealed class SortBy {
        object Score : SortBy()
        object Runtime : SortBy()
    }

    sealed class SortType {
        object Unsorted : SortType()
        object Ascending : SortType()
        object Descending : SortType()
    }

}

