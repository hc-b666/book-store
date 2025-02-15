package com.example.bookstore.book

enum class Genre {
    ALL, ROMANCE, FANTASY, CLASSICS, MYSTERY;

    fun displayName(): String = name.lowercase()
}