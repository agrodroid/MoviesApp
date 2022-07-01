package com.risajtr.moviesapp.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.risajtr.moviesapp.R
import com.risajtr.moviesapp.data.model.Movie
import com.risajtr.moviesapp.databinding.MovieItemBinding
import com.risajtr.moviesapp.utils.Constants.Companion.EMPTY_STRING
import com.risajtr.moviesapp.utils.TextUtils
import java.util.*

/**
 * Adapter for searching movies.
 */
class SearchAdapter : ListAdapter<Movie, SearchAdapter.SearchViewHolder>(DIFF_UTIL_CALLBACK_SEARCH),
    Filterable {

    private var movieList = mutableListOf<Movie>()

    // Search query
    private var searchQuery: String = EMPTY_STRING


    // ViewHolder
    class SearchViewHolder(
        private val binding: MovieItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie, searchQuery: String) {
            binding.apply {
                binding.movieTitleTv.text =
                    movie.name?.let { TextUtils.getColouredName(it, searchQuery) }
                Glide
                    .with(itemView.context)
                    .load("file:///android_asset/${movie.posterImage}")
                    .placeholder(itemView.context.getDrawable(R.drawable.placeholder_for_missing_posters))
                    .centerCrop()
                    .into(binding.moviePosterIv)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder =
        SearchViewHolder(
            MovieItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            )
        )

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem, searchQuery)
        }
    }

    fun setData(list: MutableList<Movie>) {
        this.movieList = list
        submitList(list)
    }

    fun reset() {
        searchQuery = EMPTY_STRING
        submitList(movieList)
    }

    companion object {
        private val DIFF_UTIL_CALLBACK_SEARCH = object : DiffUtil.ItemCallback<Movie>() {
            // Set to false to avoid edge cases, since there are copies of same item in the data
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                false

            // Set to false to avoid edge cases, since there are copies of same item in the data
            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                false
        }
    }

    override fun getFilter(): Filter {
        return customFilter
    }

    // Custom filter for given search query
    private val customFilter = object : Filter() {

        override fun performFiltering(constraint: CharSequence?): FilterResults {
            searchQuery = constraint.toString().lowercase(Locale.getDefault())
            val filteredList = mutableListOf<Movie>()
            if (constraint == null || constraint.isEmpty()) {
                filteredList.addAll(this@SearchAdapter.movieList)
            } else {
                for (item in this@SearchAdapter.movieList) {
                    if (item.toString().lowercase(Locale.getDefault())
                            .contains(constraint.toString().lowercase(Locale.getDefault()))
                    ) {
                        filteredList.add(item)
                    }
                }
            }
            val results = FilterResults()
            results.values = filteredList
            return results
        }

        override fun publishResults(constraint: CharSequence?, filterResults: FilterResults?) {
            val list = filterResults?.values as MutableList<Movie>?
            submitList(list)
        }

    }
}