package com.example.bookstore.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bookstore.book.BookViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateBookScreen(onNavigateBack: () -> Unit, viewModel: BookViewModel = viewModel()) {
    val bookInput by viewModel.bookInput.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    Scaffold (
        topBar = {
            TopAppBar(
                title = { Text("Book Store") },
                navigationIcon = {
                    IconButton (onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Go back"
                        )
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    modifier = Modifier.padding(16.dp),
                    selected = false,
                    onClick = { },
                    icon = {
                        Button(
                            onClick = {
                                viewModel.createBook()
                                onNavigateBack()
                            },
                            modifier = Modifier.fillMaxWidth().height(56.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary
                            ),
                            enabled = !isLoading
                        ) {
                            if (isLoading) {
                                CircularProgressIndicator(color = MaterialTheme.colorScheme.onPrimary)
                            } else {
                                Text("Create book")
                            }
                        }
                    },
                    label = { }
                )
            }
        }
    ) { paddingValues ->
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
                .padding(bottom = 160.dp)
        ) {
            if (error != null) {
                Text(
                    text = error ?: "",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }

            Text(
                text = "Enter book details to create",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            OutlinedTextField(
                value = bookInput.title,
                onValueChange = {
                    viewModel.updateBookInput(bookInput.copy(title = it))
                },
                label = { Text("Title") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )

            OutlinedTextField(
                value = bookInput.description,
                onValueChange = {
                    viewModel.updateBookInput(bookInput.copy(description = it))
                },
                label = { Text("Description") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .padding(bottom = 8.dp)
            )

            OutlinedTextField(
                value = bookInput.author,
                onValueChange = {
                    viewModel.updateBookInput(bookInput.copy(author = it))
                },
                label = { Text("Author") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )

            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = bookInput.isbn,
                    onValueChange = {
                        viewModel.updateBookInput(bookInput.copy(isbn = it))
                    },
                    label = { Text("ISBN") },
                    modifier = Modifier.weight(1f)
                )
                OutlinedTextField(
                    value = bookInput.genre,
                    onValueChange = {
                        viewModel.updateBookInput(bookInput.copy(genre = it))
                    },
                    label = { Text("Genre") },
                    modifier = Modifier.weight(1f)
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = bookInput.price.toString(),
                    onValueChange = { newValue ->
                        val newPrice = newValue.toDoubleOrNull() ?: 0.0
                        viewModel.updateBookInput(bookInput.copy(price = newPrice))
                    },
                    label = { Text("Price") },
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Decimal,
                        imeAction = ImeAction.Next
                    ),
                    singleLine = true
                )
                OutlinedTextField(
                    value = bookInput.stock.toString(),
                    onValueChange = { newValue ->
                        val newStock = newValue.toIntOrNull() ?: 0
                        viewModel.updateBookInput(bookInput.copy(stock = newStock))
                    },
                    label = { Text("Stock") },
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Next
                    ),
                    singleLine = true
                )
            }

            OutlinedTextField(
                value = bookInput.publisher,
                onValueChange = {
                    viewModel.updateBookInput(bookInput.copy(publisher = it))
                },
                label = { Text("Publisher") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )

            OutlinedTextField(
                value = bookInput.publishedDate,
                onValueChange = {
                    viewModel.updateBookInput(bookInput.copy(publishedDate = it))
                },
                label = { Text("Published Date") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )

            OutlinedTextField(
                value = bookInput.imageUrl,
                onValueChange = {
                    viewModel.updateBookInput(bookInput.copy(imageUrl = it))
                },
                label = { Text("Book image url") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )

            OutlinedTextField(
                value = bookInput.backgroundImageUrl,
                onValueChange = {
                    viewModel.updateBookInput(bookInput.copy(backgroundImageUrl = it))
                },
                label = { Text("Background image url") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )

            OutlinedTextField(
                value = bookInput.pageCount,
                onValueChange = {
                    viewModel.updateBookInput(bookInput.copy(pageCount = it))
                },
                label = { Text("Page Count") },
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
            )
        }
    }
}
