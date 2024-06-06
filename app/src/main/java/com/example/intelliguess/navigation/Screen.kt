package com.example.intelliguess.navigation

sealed class Screen(val route: String) {
    data object Home: Screen(route = "home_screen")
    data object Item: Screen(route = "item_screen")
}