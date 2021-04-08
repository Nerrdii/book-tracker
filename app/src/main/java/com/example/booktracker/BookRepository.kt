package com.example.booktracker

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class BookRepository(private val bookDao: BookDao) {
    val books: Flow<List<Book>> = bookDao.getAll()

    fun booksFromReadingList(list: ReadingList) = bookDao.getByReadingList(list)

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(book: Book) {
        bookDao.insert(book)
    }
}
