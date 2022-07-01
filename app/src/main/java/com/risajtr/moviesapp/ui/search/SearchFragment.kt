package com.risajtr.moviesapp.ui.search

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.risajtr.moviesapp.databinding.FragmentSearchBinding
import com.risajtr.moviesapp.ui.SharedViewModel
import com.risajtr.moviesapp.utils.Constants
import com.risajtr.moviesapp.utils.GridItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * Fragment for searching the loaded movies.
 */
@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    // ViewModel injected by Hilt
    private val viewModel: SharedViewModel by activityViewModels()

    private var spanCount = Constants.DEFAULT_SPAN_COUNT

    private lateinit var searchRecyclerView: RecyclerView
    private lateinit var searchAdapter: SearchAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)

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
        searchRecyclerView = binding.searchRecyclerView

        // Create and set MovieAdapter instance
        searchAdapter = SearchAdapter()

        // Setting layoutManager
        searchRecyclerView.layoutManager =
            GridLayoutManager(requireContext(), spanCount, RecyclerView.VERTICAL, false)

        // Adding ItemDecoration
        searchRecyclerView.addItemDecoration(GridItemDecoration())

        // Setting adapter to RecyclerView
        searchRecyclerView.adapter = searchAdapter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Observes moviePagingList in the viewModel and submits it to adapter asynchronously
        viewModel.movieList.observe(viewLifecycleOwner) {
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    searchAdapter.setData(it.toMutableList())
                }
            }
        }

        // Listens for change in text
        binding.includedSearchToolbar.searchToolbarEt.addTextChangedListener {
            val searchQuery = it.toString().trim()

            if (searchQuery.length < 3) {
                // Shows a toast if entered character count is less than 3
                Toast.makeText(
                    this.requireContext(),
                    "Please enter at least 3 characters!",
                    Toast.LENGTH_SHORT
                ).show()

                // Resets the list if the current search character count is less than 3
                searchAdapter.reset()
            } else {
                // Searches the list
                searchAdapter.filter.filter(searchQuery)
            }
        }

        // Click listener for clear button
        binding.includedSearchToolbar.searchToolbarClearButton.setOnClickListener {
            binding.includedSearchToolbar.searchToolbarEt.text.clear()
            searchAdapter.reset()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}