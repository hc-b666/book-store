package com.example.bookstore.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import com.example.bookstore.components.SearchBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.bookstore.GenreFilter
import com.example.bookstore.components.BookGrid

@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        SearchBar()
        GenreFilter()
        BookGrid()
    }
}
