package com.example.bookstore.book

import com.google.gson.annotations.SerializedName

data class CreateBookRequest (
    val title: String,
    val description: String,
    val isbn: String,
    val genre: String,
    val author: String,
    val publisher: String,
    @SerializedName("published_date") val publishedDate: String,
    val price: Double,
    val stock: Int,
    @SerializedName("image_url") val imageUrl: String,
    @SerializedName("background_image_url") val backgroundImageUrl: String,
    @SerializedName("page_count") val pageCount: String
)
