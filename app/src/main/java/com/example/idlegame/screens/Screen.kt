package com.example.idlegame.screens

sealed class Screen(val route: String ) {
    data object LoginScreen : Screen("loginscreen")
    data object SignupScreen : Screen("signupscreen")
    data object UserProfileScreen : Screen("userprofilescreen")
    data object SkillsScreen : Screen("skillsscreen")
}