package com.example.booktracker.viewmodels

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.booktracker.data.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BookDetailViewModel @Inject constructor(
    private val repository: BookRepository,
    private val savedStateHandle: SavedStateHandle
    )  : ViewModel() {

    private val bookId = savedStateHandle.get<Int>("bookId")!!

    fun showBook() {
        Log.d("BookDetail", bookId.toString())
    }
}