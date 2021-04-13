package com.example.booktracker.utils

import com.example.booktracker.data.GoogleBook
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type
import java.time.LocalDate
import java.time.format.DateTimeFormatterBuilder
import java.time.temporal.ChronoField

class GoogleBookDeserializer : JsonDeserializer<GoogleBook> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): GoogleBook {
        val jsonObject = json!!.asJsonObject
        val id = jsonObject.get("id").asString
        val volumeInfo = jsonObject.get("volumeInfo").asJsonObject
        val title = volumeInfo.get("title").asString
        val author = volumeInfo.get("authors")?.asJsonArray?.get(0)?.asString
        val publishedDate = volumeInfo.get("publishedDate").asString
        val description = volumeInfo.get("description")?.asString
        val genre = volumeInfo.get("categories")?.asJsonArray?.get(0)?.asString
        val imageUrl = volumeInfo.get("imageLinks")?.asJsonObject?.get("thumbnail")?.asString?.replace("http", "https")

        return GoogleBook(id, title, author, tryParse(publishedDate), description, genre, imageUrl)
    }

    private fun tryParse(dateString: String): LocalDate? {
        val format = DateTimeFormatterBuilder()
        when (dateString.length) {
            4 -> {
                format.appendPattern("yyyy")
                    .parseDefaulting(ChronoField.MONTH_OF_YEAR, 1)
                    .parseDefaulting(ChronoField.DAY_OF_MONTH, 1)
            }
            7 -> {
                format.appendPattern("yyyy-MM")
                    .parseDefaulting(ChronoField.DAY_OF_MONTH, 1)
            }
            else -> {
                format.appendPattern("yyyy-MM-dd")
            }
        }

        return LocalDate.parse(dateString, format.toFormatter())
    }
}
