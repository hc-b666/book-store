package com.example.bookstore.navigation

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object AddBook : Screen("add_book")
    data object BookDetails : Screen("book_details/{bookId}") {
        fun createRoute(bookId: Int) = "book_details/$bookId"
    }
    data object EditBook : Screen("edit_book/{bookId}") {
        fun createRoute(bookId: Int) = "edit_book/$bookId"
    }
}