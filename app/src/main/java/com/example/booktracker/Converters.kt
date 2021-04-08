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
    fun toDate(value: String?): LocalDate? {
        return value?.let { LocalDate.parse(it) }
    }

    @TypeConverter
    fun fromDate(value: LocalDate?): String? = value?.format(DateTimeFormatter.ISO_LOCAL_DATE)

}
