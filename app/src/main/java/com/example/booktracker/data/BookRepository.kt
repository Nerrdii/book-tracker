package com.example.booktracker.data

import com.example.booktracker.api.GoogleBooksService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BookRepository @Inject constructor(private val bookDao: BookDao, private val service: GoogleBooksService) {
    val books: Flow<List<Book>> = bookDao.getAll()

    fun bookById(id: Int) = bookDao.findById(id)

    fun booksFromReadingList(list: ReadingList) = bookDao.getByReadingList(list)

    suspend fun insert(book: Book) {
        bookDao.insert(book)
    }

    suspend fun update(book: Book) {
        bookDao.update(book)
    }

    suspend fun delete(book: Book) {
        bookDao.delete(book)
    }

    fun search(query: String): Flow<List<GoogleBook>> {
        return flow {
            val response = service.searchBooks(query, 0, 25)
             emit(response.items)
        }
    }
}
