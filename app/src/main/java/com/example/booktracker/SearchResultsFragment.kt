package com.example.booktracker

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.booktracker.adapters.SearchResultsAdapter
import com.example.booktracker.viewmodels.SearchResultsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchResultsFragment : Fragment() {
    private val viewModel: SearchResultsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_results, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Get search query from arguments
        val searchQuery = requireArguments().getString("searchQuery")!!

        // Set up recycler view
        val recyclerView: RecyclerView = view.findViewById(R.id.searchResultsRecyclerView)

        val adapter = SearchResultsAdapter { id ->
            // Listener for recycler view item to know which book was selected
            val bundle = bundleOf("bookId" to id)
            findNavController().navigate(R.id.action_searchResultsFragment_to_searchResultDetailFragment, bundle)
        }

        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter

        // Search books using service and show in recycler view
        lifecycleScope.launch {
            viewModel.searchBooks(searchQuery).collectLatest {
                adapter.submitList(it)
            }
        }
    }
}
