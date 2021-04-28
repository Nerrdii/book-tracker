package com.example.booktracker.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.booktracker.data.Activity
import com.example.booktracker.data.AppDatabase
import com.example.booktracker.data.Book
import com.example.booktracker.utils.LocalDateDeserializer
import com.example.booktracker.utils.LocalDateTimeDeserializer
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import kotlinx.coroutines.coroutineScope
import java.lang.Exception
import java.time.LocalDate
import java.time.LocalDateTime

// Utility class to add rows in database when it is created

class SeedDatabaseWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result = coroutineScope {
        try {
            // Read data from JSON file
            applicationContext.assets.open("books.json").use { inputStream ->
                JsonReader(inputStream.reader()).use { jsonReader ->
                    // Add deserializer so gson knows how to deal with dates
                    val gson = GsonBuilder().serializeNulls().registerTypeAdapter(LocalDate::class.java, LocalDateDeserializer()).create()
                    val bookType = object : TypeToken<List<Book>>() {}.type
                    val bookList: List<Book> = gson.fromJson(jsonReader, bookType)

                    // Add books to database
                    val database = AppDatabase.getInstance(applicationContext)
                    database.bookDao().insertAll(bookList)

                    Result.success()
                }
            }

            // Read data from JSON file
            applicationContext.assets.open("activities.json").use { inputStream ->
                JsonReader(inputStream.reader()).use { jsonReader ->
                    // Add deserializer so gson knows how to deal with datetimes
                    val gson = GsonBuilder().serializeNulls().registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeDeserializer()).create()
                    val activityType = object : TypeToken<List<Activity>>() {}.type
                    val activityList: List<Activity> = gson.fromJson(jsonReader, activityType)

                    // Add activities to database
                    val database = AppDatabase.getInstance(applicationContext)
                    database.activityDao().insertAll(activityList)

                    Result.success()
                }
            }
        } catch (ex: Exception) {
            Log.e("SeedDatabaseWorker", "Error seeding database", ex)
            Result.failure()
        }
    }
}
