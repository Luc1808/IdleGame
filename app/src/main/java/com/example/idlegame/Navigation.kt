package com.example.idlegame

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.idlegame.screens.LoginScreen
import com.example.idlegame.screens.Screen
import com.example.idlegame.screens.SignupScreen
import com.example.idlegame.screens.SkillsScreen
import com.example.idlegame.screens.UserProfileScreen
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.map

@Composable
fun Navigation(auth: FirebaseAuth, userRepository: UserRepository) {
    val navController = rememberNavController()


    var selectedIndex by rememberSaveable { mutableStateOf(0) }

    val items = listOf(
        BottomNavBarItem(
            tittle = "Home",
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home,
            route = Screen.SkillsScreen.route
        ),
        BottomNavBarItem(
            tittle = "Profile",
            selectedIcon = Icons.Filled.AccountCircle,
            unselectedIcon = Icons.Outlined.AccountCircle,
            route = Screen.UserProfileScreen.route
        ),
    )

    // List of routes where the bottom navigation bar should be shown
    val bottomNavRoutes = listOf(
        Screen.SkillsScreen.route,
        Screen.UserProfileScreen.route
    )

    // Check if the user is logged in
    val startDestination = if (auth.currentUser != null) {
        Screen.UserProfileScreen.route  // If the user is logged in, go to profile screen
    } else {
        Screen.LoginScreen.route  // If not, go to login screen
    }

    // Observe the current route
    val currentRoute by navController.currentBackStackEntryFlow
        .map { it.destination.route }
        .collectAsStateWithLifecycle(initialValue = startDestination)

    Scaffold(
        bottomBar = {
            if (currentRoute in bottomNavRoutes) {
                NavigationBar {
                    items.forEachIndexed { index, item ->
                        NavigationBarItem(
                            selected = index == selectedIndex,
                            onClick = {
                                selectedIndex = index
                                navController.navigate(item.route)
                            },
                            label = {
                                Text(text = item.tittle)
                            },
                            icon = {
                                Icon(
                                    imageVector = if (index == selectedIndex) item.selectedIcon else item.unselectedIcon,
                                    contentDescription = item.tittle
                                )
                            }
                        )
                    }
                }
            }


        }
    ) { innerPadding ->
        NavHost(navController = navController, startDestination = startDestination, Modifier.padding(innerPadding)) {
            composable(route = Screen.LoginScreen.route) {
                LoginScreen(navController = navController, auth)
            }

            composable(route = Screen.SignupScreen.route) {
                SignupScreen(navController = navController, auth, userRepository)
            }

            composable(route = Screen.UserProfileScreen.route) {
                UserProfileScreen(navController = navController, auth = auth, userRepository)
            }

            composable(route = Screen.SkillsScreen.route) {
                SkillsScreen(navController, auth, userRepository)
            }
        }
    }

}




data class BottomNavBarItem(
    val tittle: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val route: String
)
