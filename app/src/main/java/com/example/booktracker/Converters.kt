package com.example.booktracker

import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class Converters {

    @TypeConverter
    fun toReadingList(value: Int) = enumValues<ReadingList>()[value]

    @TypeConverter
    fun fromReadingList(value: ReadingList) = value.ordinal

    @TypeConverter
    fun toDate(value: String) = LocalDate.parse(value)

    @TypeConverter
    fun fromDate(value: LocalDate) = value.format(DateTimeFormatter.ISO_LOCAL_DATE)

}