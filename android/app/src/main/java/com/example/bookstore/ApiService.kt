package com.example.bookstore

import com.example.bookstore.book.Book
import com.example.bookstore.book.CreateBookRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface BookApiService {

    @GET("books")
    suspend fun getBooks(): List<Book>

    @GET("books/{id}")
    suspend fun getBookById(@Path("id") id: Int): Book

    @POST("books")
    suspend fun createBook(@Body request: CreateBookRequest): Book
}