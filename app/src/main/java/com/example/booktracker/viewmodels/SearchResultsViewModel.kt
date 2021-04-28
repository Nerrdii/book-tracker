package com.example.booktracker.viewmodels

import androidx.lifecycle.ViewModel
import com.example.booktracker.data.BookRepository
import com.example.booktracker.data.GoogleBook
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class SearchResultsViewModel @Inject constructor(private val repository: BookRepository) : ViewModel() {
    // Return API results
    fun searchBooks(query: String): Flow<List<GoogleBook>> {
        return repository.search(query)
    }
}
