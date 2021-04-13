package com.example.booktracker.data

import java.time.LocalDate

data class GoogleBook(
    val id: String,
    val title: String,
    val author: String?,
    val publishedDate: LocalDate?,
    val description: String?,
    val genre: String?,
    val imageUrl: String?,
)
