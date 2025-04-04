    package com.example.bookstore.book

    import androidx.lifecycle.ViewModel
    import androidx.lifecycle.viewModelScope
    import com.example.bookstore.RetrofitClient
    import kotlinx.coroutines.flow.MutableStateFlow
    import kotlinx.coroutines.flow.StateFlow
    import kotlinx.coroutines.flow.asStateFlow
    import kotlinx.coroutines.launch

    class BookViewModel : ViewModel() {
        private val _books = MutableStateFlow<List<Book>>(emptyList())
        val books: StateFlow<List<Book>> = _books.asStateFlow()

        private val _isLoading = MutableStateFlow(false)
        val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

        private val _error = MutableStateFlow<String?>(null)
        val error: StateFlow<String?> = _error.asStateFlow()

        private val _selectedBook = MutableStateFlow<Book?>(null)
        val selectedBook: StateFlow<Book?> = _selectedBook.asStateFlow()

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
                } finally {
                    _isLoading.value = false
                }
            }
        }

        fun updateBookInput(newInput: CreateBookRequest) {
            _bookInput.value = newInput
        }

        fun createBook() {
            viewModelScope.launch {
                _isLoading.value = true
                _error.value = null
                try {
                    RetrofitClient.bookApiService.createBook(_bookInput.value)

                    _bookInput.value = CreateBookRequest(
                        title = "", description = "", isbn = "", genre = "",
                        author = "", publisher = "", publishedDate = "", price = 0.0,
                        stock = 0, imageUrl = "", backgroundImageUrl = "", pageCount = ""
                    )

                    fetchBooks()
                } catch (e: Exception) {
                    _error.value = e.message ?: "Error creating book"
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

                    if (_selectedBook.value?.id == id) {
                        _selectedBook.value = null
                    }

                    fetchBooks()
                } catch (e: Exception) {
                    _error.value = e.message ?: "Error deleting book"
                    _isLoading.value = false
                }
            }
        }

        fun editBook(id: Int, updatedBook: CreateBookRequest) {
            viewModelScope.launch {
                _isLoading.value = true
                _error.value = null
                try {
                    RetrofitClient.bookApiService.editBook(id, updatedBook)

                    if (_selectedBook.value?.id == id) {
                        _selectedBook.value = RetrofitClient.bookApiService.getBookById(id)
                    }

                    fetchBooks()
                } catch (e: Exception) {
                    _error.value = e.message ?: "Error updating book"
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
        }
    }