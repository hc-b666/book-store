package com.example.bookstore.book

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookstore.api.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay

data class SnackbarMessage(
    val message: String,
    val isError: Boolean = false
)

data class ValidationState(
    val hasErrors: Boolean = false,
    val titleError: String? = null,
    val authorError: String? = null,
    val isbnError: String? = null,
    val priceError: String? = null
)

class BookViewModel() : ViewModel() {
    private val _books = MutableStateFlow<List<Book>>(emptyList())
    val books: StateFlow<List<Book>> = _books.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private val _selectedBook = MutableStateFlow<Book?>(null)
    val selectedBook: StateFlow<Book?> = _selectedBook.asStateFlow()

    private val _snackbarMessage = MutableStateFlow<SnackbarMessage?>(null)
    val snackbarMessage: StateFlow<SnackbarMessage?> = _snackbarMessage.asStateFlow()

    private val _validationState = MutableStateFlow(ValidationState())
    val validationState: StateFlow<ValidationState> = _validationState.asStateFlow()

    private val _bookInput = MutableStateFlow(CreateBookRequest(
        title = "",
        description = "",
        isbn = "",
        genre = "",
        author = "",
        publisher = "",
        publishedDate = "",
        price = 0.0,
        stock = 0,
        imageUrl = "",
        backgroundImageUrl = "",
        pageCount = "",
    ))
    val bookInput: StateFlow<CreateBookRequest> = _bookInput.asStateFlow()

    init {
        fetchBooks()
    }

    fun fetchBooks() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val fetchedBooks = RetrofitClient.bookApiService.getBooks()
                _books.value = fetchedBooks.toList()
            } catch (e: Exception) {
                _error.value = e.message ?: "Unknown error occurred"
                showSnackbar(e.message ?: "Unknown error occurred", true)
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun getBookById(id: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                _selectedBook.value = RetrofitClient.bookApiService.getBookById(id)
            } catch (e: Exception) {
                _error.value = e.message ?: "Error fetching book details"
                showSnackbar(e.message ?: "Error fetching book details", true)
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun updateBookInput(newInput: CreateBookRequest) {
        _bookInput.value = newInput
        _validationState.value = ValidationState()
    }

    private fun validateBookInput(): Boolean {
        val input = _bookInput.value
        val titleError = if (input.title.isBlank()) "Title is required" else null
        val authorError = if (input.author.isBlank()) "Author is required" else null
        val isbnError = if (input.isbn.isBlank()) "ISBN is required" else null
        val priceError = if (input.price <= 0) "Price must be greater than 0" else null

        val hasErrors = titleError != null || authorError != null || isbnError != null || priceError != null

        _validationState.value = ValidationState(
            hasErrors = hasErrors,
            titleError = titleError,
            authorError = authorError,
            isbnError = isbnError,
            priceError = priceError
        )

        return !hasErrors
    }

    fun createBook() {
        if (!validateBookInput()) {
            showSnackbar("Please fix the validation errors", true)
            return
        }

        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                RetrofitClient.bookApiService.createBook(_bookInput.value)

                showSnackbar("Book created successfully")

                _bookInput.value = CreateBookRequest(
                    title = "", description = "", isbn = "", genre = "",
                    author = "", publisher = "", publishedDate = "", price = 0.0,
                    stock = 0, imageUrl = "", backgroundImageUrl = "", pageCount = ""
                )

                fetchBooks()
            } catch (e: Exception) {
                _error.value = e.message ?: "Error creating book"
                showSnackbar(e.message ?: "Error creating book", true)
                _isLoading.value = false
            }
        }
    }

    fun deleteBook(id: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                RetrofitClient.bookApiService.deleteBook(id)

                showSnackbar("Book deleted successfully")

                if (_selectedBook.value?.id == id) {
                    _selectedBook.value = null
                }

                fetchBooks()
            } catch (e: Exception) {
                _error.value = e.message ?: "Error deleting book"
                showSnackbar(e.message ?: "Error deleting book", true)
                _isLoading.value = false
            }
        }
    }

    fun editBook(id: Int, updatedBook: CreateBookRequest) {
        if (!validateBookInput()) {
            showSnackbar("Please fix the validation errors", true)
            return
        }

        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                RetrofitClient.bookApiService.editBook(id, updatedBook)

                showSnackbar("Book updated successfully")

                if (_selectedBook.value?.id == id) {
                    _selectedBook.value = RetrofitClient.bookApiService.getBookById(id)
                }

                fetchBooks()
            } catch (e: Exception) {
                _error.value = e.message ?: "Error updating book"
                showSnackbar(e.message ?: "Error updating book", true)
                _isLoading.value = false
            }
        }
    }

    fun prepareForEditing(book: Book) {
        _bookInput.value = CreateBookRequest(
            title = book.title,
            description = book.description,
            isbn = book.isbn,
            genre = book.genre,
            author = book.author,
            publisher = book.publisher,
            publishedDate = book.publishedDate,
            price = book.price,
            stock = book.stock,
            imageUrl = book.imageUrl,
            backgroundImageUrl = book.backgroundImageUrl,
            pageCount = book.pageCount
        )
        _validationState.value = ValidationState()
    }

    private fun showSnackbar(message: String, isError: Boolean = false) {
        _snackbarMessage.value = SnackbarMessage(message, isError)
        viewModelScope.launch {
            delay(3000)
            _snackbarMessage.value = null
        }
    }

    fun dismissSnackbar() {
        _snackbarMessage.value = null
    }
}
