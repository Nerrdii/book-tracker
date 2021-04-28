package com.example.booktracker.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.booktracker.data.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class BookDetailViewModel @Inject constructor(
    private val bookRepository: BookRepository,
    private val activityRepository: ActivityRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    // Get book ID from navigation argument
    private val bookId = savedStateHandle.get<Int>("bookId")!!
    val book = bookRepository.bookById(bookId).asLiveData()

    // Remove book from database using coroutine to run on separate thread
    fun delete() = viewModelScope.launch {
        val title = this@BookDetailViewModel.book.value?.title
        withContext(Dispatchers.IO) {
            bookRepository.delete(book.value!!)
            activityRepository.insert(
                Activity(
                    ActivityType.DELETE,
                    "You removed $title from your library"
                )
            )
        }
    }
}
