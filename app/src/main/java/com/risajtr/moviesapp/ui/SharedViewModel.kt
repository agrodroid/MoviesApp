package com.risajtr.moviesapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.risajtr.moviesapp.data.model.Movie
import com.risajtr.moviesapp.data.repository.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * The SharedViewModel which is used by both MoviesFragment and SearchFragment.
 */
@HiltViewModel
class SharedViewModel @Inject constructor(moviesRepository: MoviesRepository) : ViewModel() {

    // Caches the LiveData<PagingData<Movie>> in viewModelScope
    val moviePagingList = moviesRepository.fetchMovies().cachedIn(viewModelScope)

    // LiveData object for storing the LiveData version of the currently loaded movie list
    private val _movieList = MutableLiveData<List<Movie>>()
    val movieList: LiveData<List<Movie>>
        get() = _movieList

    // Sets value to _movieList
    fun setMovieList(movieList: List<Movie>) {
        _movieList.value = movieList
    }

}