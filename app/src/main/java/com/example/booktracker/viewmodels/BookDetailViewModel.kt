package com.example.booktracker.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.booktracker.data.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BookDetailViewModel @Inject constructor(
    repository: BookRepository,
    savedStateHandle: SavedStateHandle
    )  : ViewModel() {

    private val bookId = savedStateHandle.get<Int>("bookId")!!
    val book = repository.bookById(bookId).asLiveData()
}