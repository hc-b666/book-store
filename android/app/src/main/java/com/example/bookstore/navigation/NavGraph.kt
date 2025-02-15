package com.example.bookstore.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.bookstore.screens.BookDetailsScreen
import com.example.bookstore.screens.CreateBookScreen
import com.example.bookstore.screens.HomeScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(route = Screen.Home.route) {
            HomeScreen(
                onNavigateToAddBook = {
                    navController.navigate(Screen.AddBook.route)
                },
                onNavigateToDetails = {
                    navController.navigate(Screen.BookDetails.route)
                }
            )
        }
        composable(route = Screen.AddBook.route) {
            CreateBookScreen(
                onNavigateBack = {
                    navController.navigateUp()
                }
            )
        }
        composable(route = Screen.BookDetails.route) {
            BookDetailsScreen(
                onNavigateBack = {
                    navController.navigateUp()
                }
            )
        }
    }
}