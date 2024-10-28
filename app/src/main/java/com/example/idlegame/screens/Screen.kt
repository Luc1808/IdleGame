package com.example.idlegame.screens

sealed class Screen(val route: String ) {
    object LoginScreen : Screen("loginscreen")
    object SignupScreen : Screen("signupscreen")
    object UserProfileScreen : Screen("userprofilescreen")
}