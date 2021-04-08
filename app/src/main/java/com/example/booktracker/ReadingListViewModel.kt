package com.example.booktracker

import android.util.Log
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReadingListViewModel @Inject constructor(private val repository: BookRepository) : ViewModel() {
    private val _filter: MutableLiveData<ReadingList> = MutableLiveData(ReadingList.WANT_TO_READ)
    val books: LiveData<List<Book>> = Transformations.switchMap(_filter) {
        val filteredList = repository.booksFromReadingList(it)
        filteredList.asLiveData()
    }

    fun updateReadingList(list: ReadingList) {
        _filter.postValue(list)
    }
}
