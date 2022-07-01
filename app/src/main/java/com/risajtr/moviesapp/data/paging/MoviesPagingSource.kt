package com.risajtr.moviesapp.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.risajtr.moviesapp.data.DataSource
import com.risajtr.moviesapp.data.model.Movie
import com.risajtr.moviesapp.utils.Constants.Companion.STARTING_PAGE_INDEX
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * The paging source class required by Paging3 library.
 * @param dataSource [DataSource].
 */
class MoviesPagingSource @Inject constructor(private val dataSource: DataSource) :
    PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {

        // Current position
        val position = params.key ?: STARTING_PAGE_INDEX

        return try {

            // Fetches data in the background
            val response = withContext(Dispatchers.IO) { dataSource.fetchMovies(position) }
            val moviesList = response?.page?.contentItems?.content

            LoadResult.Page(
                data = moviesList as List<Movie>,
                prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (moviesList.isEmpty()) null else position + 1
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }

    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val anchorPage = state.closestPageToPosition(anchorPosition) ?: return null
        return anchorPage.prevKey?.plus(1) ?: anchorPage.nextKey?.minus(1)
    }

}