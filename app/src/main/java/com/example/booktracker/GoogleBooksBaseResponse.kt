package com.example.booktracker

data class GoogleBooksBaseResponse<E>(
    val kind: String,
    val totalItems: Int,
    val items: List<E>
)
