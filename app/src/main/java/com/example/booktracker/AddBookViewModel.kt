package com.example.booktracker

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddBookViewModel @Inject constructor(private val repository: BookRepository) : ViewModel() {
    fun insert(book: Book) = viewModelScope.launch {
        repository.insert(book)
    }
}
