package com.risajtr.moviesapp.ui.movies

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.risajtr.moviesapp.R
import com.risajtr.moviesapp.databinding.FragmentMoviesBinding
import com.risajtr.moviesapp.ui.SharedViewModel
import com.risajtr.moviesapp.utils.Constants
import com.risajtr.moviesapp.utils.GridItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * Fragment for listing movies from PagingData.
 */
@AndroidEntryPoint
class MoviesFragment : Fragment() {

    private var _binding: FragmentMoviesBinding? = null
    private val binding get() = _binding!!

    // ViewModel injected by Hilt
    private val viewModel: SharedViewModel by activityViewModels()

    private var spanCount = Constants.DEFAULT_SPAN_COUNT

    private lateinit var moviesRecyclerView: RecyclerView
    private lateinit var movieAdapter: MovieAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoviesBinding.inflate(inflater, container, false)

        // Checks span count and adjusts based on orientation every time onCreateView() is called
        spanCount = if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            3
        } else {
            7
        }

        initRecyclerView()
        return binding.root
    }

    private fun initRecyclerView() {
        // Setting RecyclerView
        moviesRecyclerView = binding.moviesRecyclerView

        // Create and set MovieAdapter instance
        movieAdapter = MovieAdapter()

        // Setting layoutManager
        moviesRecyclerView.layoutManager =
            GridLayoutManager(requireContext(), spanCount, RecyclerView.VERTICAL, false)

        // Adding ItemDecoration
        moviesRecyclerView.addItemDecoration(GridItemDecoration())

        // Setting adapter to RecyclerView
        moviesRecyclerView.adapter = movieAdapter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Observes moviePagingList in the viewModel and submits it to adapter asynchronously
        viewModel.moviePagingList.observe(viewLifecycleOwner) {
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    if (it == null) {
                        binding.progressBar.visibility = View.VISIBLE
                    } else {
                        binding.progressBar.visibility = View.GONE
                        movieAdapter.submitData(it)
                    }
                }
            }
        }

        // Observes the LoadStateFlow from adapter asynchronously to show progressbar
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                movieAdapter.loadStateFlow.collect {
                    if (it.source.append is LoadState.Loading || it.source.prepend is LoadState.Loading) {
                        binding.progressBar.visibility = View.VISIBLE
                    } else {
                        binding.progressBar.visibility = View.GONE
                    }
                }
            }
        }

        // Click listener for back button
        binding.includedMoviesToolbar.toolbarBackButton.setOnClickListener {
            // Back button action
            activity?.onBackPressed()
        }

        // Click listener for search button
        binding.includedMoviesToolbar.toolbarSearchButton.setOnClickListener {
            // Saves a copy of current data as normal LiveData for use by SearchFragment
            viewModel.setMovieList(movieAdapter.snapshot().items)

            // Navigation to SearchFragment
            view.findNavController().navigate(R.id.action_moviesFragment_to_searchFragment)
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}