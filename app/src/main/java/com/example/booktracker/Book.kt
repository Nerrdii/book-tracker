package com.example.booktracker

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.time.LocalDate

@Entity(tableName = "books")
data class Book(
    @ColumnInfo val title: String?,
    @ColumnInfo val author: String?,
    @ColumnInfo val publishedDate: LocalDate?,
    @ColumnInfo val imageUrl: String?,
    @ColumnInfo val readingList: ReadingList?,
    @ColumnInfo val startDate: LocalDate?,
    @ColumnInfo val finishDate: LocalDate?,
    @ColumnInfo val rating: Int?,
    @ColumnInfo val review: String?,
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
)

enum class ReadingList(private val friendlyName: String) {
    @SerializedName("0")
    WANT_TO_READ("Want to Read"),
    @SerializedName("1")
    READING("Reading"),
    @SerializedName("2")
    READ("Read");

    override fun toString(): String {
        return friendlyName
    }
}
