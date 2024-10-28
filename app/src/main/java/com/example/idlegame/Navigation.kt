package com.example.idlegame

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.idlegame.screens.LoginScreen
import com.example.idlegame.screens.Screen
import com.example.idlegame.screens.SignupScreen
import com.example.idlegame.screens.UserProfileScreen
import com.google.firebase.auth.FirebaseAuth

@Composable
fun Navigation(auth: FirebaseAuth, userRepository: UserRepository) {
    val navController = rememberNavController()

    // Check if the user is logged in
    val startDestination = if (auth.currentUser != null) {
        Screen.UserProfileScreen.route  // If the user is logged in, go to profile screen
    } else {
        Screen.LoginScreen.route  // If not, go to login screen
    }

    NavHost(navController = navController, startDestination = startDestination) {
        composable(route = Screen.LoginScreen.route) {
            LoginScreen(navController = navController, auth)
        }

        composable(route = Screen.SignupScreen.route) {
            SignupScreen(navController = navController, auth, userRepository)
        }

        composable(route = Screen.UserProfileScreen.route) {
            UserProfileScreen(navController = navController, auth = auth, userRepository)
        }
    }
}