package com.example.booktracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class SearchFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // View references
        val searchQueryEditText: EditText = view.findViewById(R.id.searchQueryEditText)
        val searchButton: Button = view.findViewById(R.id.searchButton)
        val addManuallyButton: Button = view.findViewById(R.id.addManuallyButton)

        searchButton.setOnClickListener {
            val searchQuery = searchQueryEditText.text.toString()
            // Validation
            if (searchQuery.isEmpty()) {
                searchQueryEditText.error = "Please provide a search value"
            } else {
                // Navigate to fragment with search query
                val bundle = bundleOf("searchQuery" to searchQuery)
                findNavController().navigate(R.id.action_searchFragment_to_searchResultsFragment, bundle)
            }
        }

        // Navigate to fragment to add book
        addManuallyButton.setOnClickListener {
            findNavController().navigate(R.id.action_searchFragment_to_addBookFragment)
        }
    }
}
