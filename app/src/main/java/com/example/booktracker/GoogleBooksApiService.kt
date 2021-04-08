package com.example.booktracker

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private const val BASE_URL = "https://www.googleapis.com/books/v1/"

private val retrofit: Retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

interface GoogleBooksApiService {
    @GET("volumes")
    suspend fun search(@Query("q") q: String): GoogleBooksBaseResponse<GoogleBook>

    @GET("volumes/{id}")
    suspend fun getVolumeById(@Path("id") id: String): GoogleBook
}

object GoogleBooksApi {
    val retrofitService: GoogleBooksApiService by lazy { retrofit.create(GoogleBooksApiService::class.java) }
}
