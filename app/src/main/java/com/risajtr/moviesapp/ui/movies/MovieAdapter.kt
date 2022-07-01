package com.risajtr.moviesapp.ui.movies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.risajtr.moviesapp.R
import com.risajtr.moviesapp.data.model.Movie
import com.risajtr.moviesapp.databinding.MovieItemBinding

/**
 * PagingDataAdapter for listing movies.
 */
class MovieAdapter : PagingDataAdapter<Movie, MovieAdapter.MovieViewHolder>(DIFF_UTIL_CALLBACK) {

    // ViewHolder
    class MovieViewHolder(
        private val binding: MovieItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie) {
            binding.apply {
                binding.movieTitleTv.text = movie.name
                Glide
                    .with(itemView.context)
                    .load("file:///android_asset/${movie.posterImage}")
                    .placeholder(itemView.context.getDrawable(R.drawable.placeholder_for_missing_posters))
                    .centerCrop()
                    .into(binding.moviePosterIv)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder =
        MovieViewHolder(
            MovieItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            )
        )

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    // DiffUtil callback
    companion object {
        private val DIFF_UTIL_CALLBACK = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem.name == newItem.name && oldItem.posterImage == newItem.posterImage

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem == newItem
        }
    }
}