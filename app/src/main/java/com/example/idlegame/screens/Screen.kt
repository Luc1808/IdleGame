package com.example.idlegame.screens

sealed class Screen(val route: String) {
    object Skills : Screen("skills_screen")
    object Profile : Screen("profile_screen")
    object Shopping : Screen("shopping_screen")
}