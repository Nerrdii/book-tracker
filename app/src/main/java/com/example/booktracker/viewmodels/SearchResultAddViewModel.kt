package com.example.booktracker.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.booktracker.data.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchResultAddViewModel @Inject constructor(
    private val bookRepository: BookRepository,
    private val activityRepository: ActivityRepository
) : ViewModel() {
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
