package com.example.booktracker.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.booktracker.data.AppDatabase
import com.example.booktracker.data.Book
import com.example.booktracker.utils.LocalDateDeserializer
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import kotlinx.coroutines.coroutineScope
import java.lang.Exception
import java.time.LocalDate

class SeedDatabaseWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result = coroutineScope {
        try {
            applicationContext.assets.open("books.json").use { inputStream ->
                JsonReader(inputStream.reader()).use { jsonReader ->
                    val gson = GsonBuilder().serializeNulls().registerTypeAdapter(LocalDate::class.java, LocalDateDeserializer()).create()
                    val bookType = object : TypeToken<List<Book>>() {}.type
                    val bookList: List<Book> = gson.fromJson(jsonReader, bookType)

                    val database = AppDatabase.getInstance(applicationContext)
                    database.bookDao().insertAll(bookList)

                    Result.success()
                }
            }
        } catch (ex: Exception) {
            Log.e("SeedDatabaseWorker", "Error seeding database", ex)
            Result.failure()
        }
    }
}
