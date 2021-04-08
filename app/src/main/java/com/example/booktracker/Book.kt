package com.example.booktracker

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "books")
data class Book(
    @ColumnInfo val title: String?,
    @ColumnInfo val author: String?,
    @ColumnInfo val publishedDate: LocalDate?,
    @ColumnInfo val readingList: ReadingList?,
    @ColumnInfo val imageUrl: String?,
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
)

enum class ReadingList(private val friendlyName: String) {
    WANT_TO_READ("Want to Read"),
    READING("Reading"),
    READ("Read");

    override fun toString(): String {
        return friendlyName
    }
}
