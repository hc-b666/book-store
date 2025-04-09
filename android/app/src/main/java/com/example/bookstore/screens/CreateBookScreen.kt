package com.example.bookstore.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.filled.Close
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
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bookstore.R
import com.example.bookstore.book.BookViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateBookScreen(onNavigateBack: () -> Unit, viewModel: BookViewModel = viewModel()) {
    val bookInput by viewModel.bookInput.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val snackbarMessage by viewModel.snackbarMessage.collectAsState()
    val validationState by viewModel.validationState.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(snackbarMessage) {
        snackbarMessage?.let {
            snackbarHostState.showSnackbar(it.message)
        }
    }

    Scaffold (
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.app_name)) },
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
                                Text(stringResource(R.string.button_create_book))
                            }
                        }
                    },
                    label = { }
                )
            }
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) { data ->
                Box(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    contentAlignment = Alignment.TopCenter
                ) {
                    Snackbar(
                        modifier = Modifier.fillMaxWidth(),
                        action = {
                            IconButton(onClick = { viewModel.dismissSnackbar() }) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Dismiss"
                                )
                            }
                        },
                        containerColor = if (snackbarMessage?.isError == true)
                            MaterialTheme.colorScheme.errorContainer
                        else
                            MaterialTheme.colorScheme.primaryContainer,
                        contentColor = if (snackbarMessage?.isError == true)
                            MaterialTheme.colorScheme.onErrorContainer
                        else
                            MaterialTheme.colorScheme.onPrimaryContainer
                    ) {
                        Text(data.visuals.message)
                    }
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
                .padding(bottom = 160.dp)
        ) {
            Text(
                text = stringResource(R.string.create_book_title),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            OutlinedTextField(
                value = bookInput.title,
                onValueChange = {
                    viewModel.updateBookInput(bookInput.copy(title = it))
                },
                label = { Text(stringResource(R.string.label_title)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = if (validationState.titleError != null) 0.dp else 8.dp),
                isError = validationState.titleError != null,
                supportingText = {
                    validationState.titleError?.let {
                        Text(
                            text = it,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            )

            OutlinedTextField(
                value = bookInput.description,
                onValueChange = {
                    viewModel.updateBookInput(bookInput.copy(description = it))
                },
                label = { Text(stringResource(R.string.label_description)) },
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
                label = { Text(stringResource(R.string.label_author)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = if (validationState.authorError != null) 0.dp else 8.dp),
                isError = validationState.authorError != null,
                supportingText = {
                    validationState.authorError?.let {
                        Text(
                            text = it,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = if (validationState.isbnError != null) 0.dp else 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = bookInput.isbn,
                    onValueChange = {
                        viewModel.updateBookInput(bookInput.copy(isbn = it))
                    },
                    label = { Text(stringResource(R.string.label_isbn)) },
                    modifier = Modifier.weight(1f),
                    isError = validationState.isbnError != null,
                    supportingText = {
                        validationState.isbnError?.let {
                            Text(
                                text = it,
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                )
                OutlinedTextField(
                    value = bookInput.genre,
                    onValueChange = {
                        viewModel.updateBookInput(bookInput.copy(genre = it))
                    },
                    label = { Text(stringResource(R.string.label_genre)) },
                    modifier = Modifier.weight(1f)
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = if (validationState.priceError != null) 0.dp else 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = bookInput.price.toString(),
                    onValueChange = { newValue ->
                        val newPrice = newValue.toDoubleOrNull() ?: 0.0
                        viewModel.updateBookInput(bookInput.copy(price = newPrice))
                    },
                    label = { Text(stringResource(R.string.label_price)) },
                    modifier = Modifier.weight(1f),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Decimal,
                        imeAction = ImeAction.Next
                    ),
                    singleLine = true,
                    isError = validationState.priceError != null,
                    supportingText = {
                        validationState.priceError?.let {
                            Text(
                                text = it,
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                )
                OutlinedTextField(
                    value = bookInput.stock.toString(),
                    onValueChange = { newValue ->
                        val newStock = newValue.toIntOrNull() ?: 0
                        viewModel.updateBookInput(bookInput.copy(stock = newStock))
                    },
                    label = { Text(stringResource(R.string.label_stock)) },
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
                label = { Text(stringResource(R.string.label_publisher)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )

            OutlinedTextField(
                value = bookInput.publishedDate,
                onValueChange = {
                    viewModel.updateBookInput(bookInput.copy(publishedDate = it))
                },
                label = { Text(stringResource(R.string.label_published_date)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )

            OutlinedTextField(
                value = bookInput.imageUrl,
                onValueChange = {
                    viewModel.updateBookInput(bookInput.copy(imageUrl = it))
                },
                label = { Text(stringResource(R.string.label_image_url)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )

            OutlinedTextField(
                value = bookInput.backgroundImageUrl,
                onValueChange = {
                    viewModel.updateBookInput(bookInput.copy(backgroundImageUrl = it))
                },
                label = { Text(stringResource(R.string.label_background_image)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )

            OutlinedTextField(
                value = bookInput.pageCount,
                onValueChange = {
                    viewModel.updateBookInput(bookInput.copy(pageCount = it))
                },
                label = { Text(stringResource(R.string.label_page_count)) },
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
            )
        }
    }
}
