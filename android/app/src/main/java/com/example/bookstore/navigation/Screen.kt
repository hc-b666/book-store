package com.example.bookstore.navigation

sealed class Screen(val route: String) {
    data object Home : Screen("home_screen")
    data object AddBook : Screen("add_book_screen")
    data object BookDetails: Screen("book_details_screen")
}