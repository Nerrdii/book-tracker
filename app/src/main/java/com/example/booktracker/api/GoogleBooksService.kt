package com.example.booktracker.api

import com.example.booktracker.data.GoogleBook
import com.example.booktracker.data.GoogleBooksSearchResponse
import com.example.booktracker.utils.GoogleBookDeserializer
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface GoogleBooksService {
    @GET("volumes")
    suspend fun searchBooks(
        @Query("q") query: String,
        @Query("startIndex") startIndex: Int,
        @Query("maxResults") maxResults: Int
    ): GoogleBooksSearchResponse

    companion object {
        private const val BASE_URL = "https://www.googleapis.com/books/v1/"

        fun create(): GoogleBooksService {
            val gson = GsonBuilder()
                .registerTypeAdapter(GoogleBook::class.java, GoogleBookDeserializer())
                .create()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(GoogleBooksService::class.java)
        }
    }
}
