package com.example.bookstore

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.bookstore.book.Genre

@Composable
fun GenreFilter() {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    ) {
        items(Genre.values().toList()) { genre ->
            FilterChip(
                selected = genre == Genre.FANTASY,
                onClick = { /* Handle genre selection */ },
                label = { Text(genre.displayName()) }
            )
        }
    }
}