package com.risajtr.moviesapp.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.risajtr.moviesapp.data.DataSource
import com.risajtr.moviesapp.data.model.Movie
import com.risajtr.moviesapp.data.paging.MoviesPagingSource
import com.risajtr.moviesapp.utils.Constants.Companion.PAGE_SIZE
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository class for MoviesApp.
 * @param dataSource An instance of [DataSource].
 */
@Singleton
class MoviesRepository @Inject constructor(dataSource: DataSource) {

    // Creates an instance of MoviesPagingSource.
    private val moviesPagingSource = MoviesPagingSource(dataSource)

    /**
     * Fetches the list of movies from [MoviesPagingSource] and returns it as [LiveData].
     */
    fun fetchMovies(): LiveData<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE, enablePlaceholders = false),
            pagingSourceFactory = { moviesPagingSource }
        )
            .liveData
    }

}