package com.example.booktracker

import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.launch

class ReadingListViewModel(private val repository: BookRepository) : ViewModel() {
    private val _filter: MutableLiveData<ReadingList> = MutableLiveData(ReadingList.WANT_TO_READ)
    val books: LiveData<List<Book>> = Transformations.switchMap(_filter) {
        val filteredList = repository.booksFromReadingList(it)
        filteredList.asLiveData()
    }

    fun insert(book: Book) = viewModelScope.launch {
        repository.insert(book)
    }

    fun updateReadingList(list: ReadingList) {
        _filter.postValue(list)
    }
}

class ReadingListViewModelFactory(private val repository: BookRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ReadingListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ReadingListViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}