package com.example.bookstore.book

import com.google.gson.annotations.SerializedName

data class Book(
    @SerializedName("ID") val id: Int,
    @SerializedName("CreatedAt") val createdAt: String,
    @SerializedName("UpdatedAt") val updatedAt: String,
    @SerializedName("DeletedAt") val deletedAt: String?,
    val title: String,
    val description: String,
    val isbn: String,
    val genre: String,
    @SerializedName("page_count") val pageCount: String,
    val author: String,
    val publisher: String,
    @SerializedName("published_date") val publishedDate: String,
    val price: Double,
    val stock: Float,
    @SerializedName("image_url") val imageUrl: String,
    @SerializedName("background_image_url") val backgroundImageUrl: String
)
