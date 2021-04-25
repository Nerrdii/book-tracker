package com.example.booktracker.utils

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

class DateUtils {

    companion object {
        fun dateToString(date: LocalDate): String {
            return date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM))
        }

        fun dateTimeToString(date: LocalDateTime): String {
            return date.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM))
        }
    }

}
