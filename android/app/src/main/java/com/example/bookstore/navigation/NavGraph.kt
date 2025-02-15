package com.example.bookstore.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
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
    }
}