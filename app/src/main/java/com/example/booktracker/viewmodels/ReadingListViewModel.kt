package com.example.booktracker.viewmodels

import androidx.lifecycle.*
import com.example.booktracker.data.Book
import com.example.booktracker.data.BookRepository
import com.example.booktracker.data.ReadingList
import dagger.hilt.android.lifecycle.HiltViewModel
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
