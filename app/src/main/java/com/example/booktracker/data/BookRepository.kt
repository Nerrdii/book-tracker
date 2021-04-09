package com.example.booktracker.data

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BookRepository @Inject constructor(private val bookDao: BookDao) {
    val books: Flow<List<Book>> = bookDao.getAll()

    fun bookById(id: Int) = bookDao.findById(id)

    fun booksFromReadingList(list: ReadingList) = bookDao.getByReadingList(list)

    suspend fun insert(book: Book) {
        bookDao.insert(book)
    }
}
