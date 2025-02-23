package com.example.bookstore

import com.example.bookstore.book.Book
import retrofit2.http.GET
import retrofit2.http.Path

interface BookApiService {

    @GET("books")
    suspend fun getBooks(): List<Book>

    @GET("books/{id}")
    suspend fun getBookById(@Path("id") id: Int): Book
}