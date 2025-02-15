package com.example.bookstore.book

data class Book(
    val id: Int,
    val title: String,
    val author: String,
    val price: Double,
    val rating: Float,
    val coverUrl: String,
    val genre: String
)
