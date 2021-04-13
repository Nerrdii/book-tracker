package com.example.booktracker.data

data class GoogleBooksSearchResponse(
    val kind: String,
    val totalItems: Int,
    val items: List<GoogleBook>
)
