package com.example.booktracker.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.booktracker.data.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditBookViewModel @Inject constructor(
    private val bookRepository: BookRepository,
    private val activityRepository: ActivityRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val bookId = savedStateHandle.get<Int>("bookId")!!
    val book = bookRepository.bookById(bookId).asLiveData()

    fun update(book: Book) = viewModelScope.launch {
        bookRepository.update(book)
        if (this@EditBookViewModel.book.value?.readingList != book.readingList) {
            activityRepository.insert(
                Activity(
                    ActivityType.UPDATE,
                    "You moved ${book.title} to your ${book.readingList} list"
                )
            )
        }
    }
}