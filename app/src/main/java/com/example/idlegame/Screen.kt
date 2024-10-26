package com.example.idlegame

sealed class Screen(val route: String ) {
    object LoginScreen : Screen("loginscreen")
    object SignupScreen : Screen("signupscreen")
    object MainScreen: Screen("mainscreen")
    object ProfileScreen: Screen("profilescreen")
}