package com.example.intelliguess.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.intelliguess.IntelliGuessViewModel
import com.example.intelliguess.presentation.IntelliGuessApp
import com.example.intelliguess.presentation.IntelliGuessCollection
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.activity.viewModels


@Composable
fun SetupNavGraph(
    navController: NavHostController,
    viewModel: IntelliGuessViewModel
) {
    // Handles navigation between destinations
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route // Start-up destination when the application runs
    ) {
        composable(
            route = Screen.Home.route
        ) {
            IntelliGuessApp(navController, viewModel)
            viewModel.isInHomeScreen.value = true
        }
        composable(
            route = Screen.Item.route
        ) {
            IntelliGuessCollection(navController, viewModel)
            viewModel.isInHomeScreen.value = false
        }
    }
}


