package com.example.booktracker.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDao {
    @Query("SELECT * FROM books")
    fun getAll(): Flow<List<Book>>

    @Query("SELECT * FROM books WHERE readingList = :list")
    fun getByReadingList(list: ReadingList): Flow<List<Book>>

    @Query("SELECT * FROM books WHERE id = :id")
    fun findById(id: Int): Flow<Book>

    @Insert
    suspend fun insertAll(books: List<Book>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(book: Book)

    @Delete
    suspend fun delete(book: Book)
}
