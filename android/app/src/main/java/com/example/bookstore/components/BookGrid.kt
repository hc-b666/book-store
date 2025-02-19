package com.example.bookstore.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.example.bookstore.book.Book

@Composable
fun BookGrid(
    books: List<Book>,
    onNavigateToDetails: (Int) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(
            items = books,
            key = { book -> book.id }
        ) { book ->
            BookCard(
                book = book,
                onNavigateToDetails = { onNavigateToDetails(book.id) }
            )
        }
    }
}