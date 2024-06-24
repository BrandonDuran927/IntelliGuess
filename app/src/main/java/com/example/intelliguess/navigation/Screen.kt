package com.example.intelliguess.navigation

sealed class Screen(val route: String) {
    // Destination name of home
    data object Home: Screen(route = "home_screen")
    // Destination name of items
    data object Item: Screen(route = "item_screen")
}