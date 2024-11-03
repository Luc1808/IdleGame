package com.example.idlegame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.idlegame.ui.theme.IdleGameTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel = SkillsViewModel()
        setContent {
            val navController = rememberNavController()
           
            NavHost(navController = navController, startDestination = Screen.Skills.route) {
                composable(Screen.Skills.route) { SkillsScreen(viewModel, navController) }
                composable(Screen.Profile.route) { ProfileScreen(viewModel, navController) }
                composable(Screen.Shopping.route) { ShoppingScreen(navController) }
            }
        }
    }
}


