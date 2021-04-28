package com.example.booktracker.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.booktracker.data.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

// Annotation lets view model use dependency injection to get access to repository classes
@HiltViewModel
class AddBookViewModel @Inject constructor(
    private val bookRepository: BookRepository,
    private val activityRepository: ActivityRepository
) : ViewModel() {
    // Add book to database using coroutine to run on separate thread
    fun insert(book: Book) = viewModelScope.launch {
        bookRepository.insert(book)
        activityRepository.insert(
            Activity(
                ActivityType.INSERT,
                "You added ${book.title} to your ${book.readingList} list"
            )
        )
    }
}
