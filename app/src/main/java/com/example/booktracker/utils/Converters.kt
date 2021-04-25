package com.example.booktracker.utils

import androidx.room.TypeConverter
import com.example.booktracker.data.ActivityType
import com.example.booktracker.data.ReadingList
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Converters {

    @TypeConverter
    fun toReadingList(value: Int) = enumValues<ReadingList>()[value]

    @TypeConverter
    fun fromReadingList(value: ReadingList) = value.ordinal

    @TypeConverter
    fun toActivityType(value: Int) = enumValues<ActivityType>()[value]

    @TypeConverter
    fun fromActivityType(value: ActivityType) = value.ordinal

    @TypeConverter
    fun toDate(value: String?): LocalDate? {
        return value?.let { LocalDate.parse(it) }
    }

    @TypeConverter
    fun fromDate(value: LocalDate?): String? = value?.format(DateTimeFormatter.ISO_LOCAL_DATE)

    @TypeConverter
    fun toDateTime(value: String?): LocalDateTime? {
        return value?.let { LocalDateTime.parse(it) }
    }

    @TypeConverter
    fun fromDateTime(value: LocalDateTime?): String? = value?.format(DateTimeFormatter.ISO_DATE_TIME)

}
