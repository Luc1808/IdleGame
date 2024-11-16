package com.example.idlegame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.idlegame.models.database.AppDatabase
import com.example.idlegame.views.ProfileScreen
import com.example.idlegame.views.Screen
import com.example.idlegame.views.ShoppingScreen
import com.example.idlegame.views.SkillsScreen
import com.example.idlegame.viewmodels.SkillsViewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        deleteDatabase("idlegame-db")

        val buttonDataDao = AppDatabase.getDatabase(applicationContext).buttonDataDao()
        val shopItemDao = AppDatabase.getDatabase(applicationContext).shopItemDao()
        val userProgressDao = AppDatabase.getDatabase(applicationContext).userProgressDao()


        val viewModel = SkillsViewModel(buttonDataDao, shopItemDao, userProgressDao)
        setContent {
            val navController = rememberNavController()

            val items = listOf(
                NavBarItem(
                    title = "Shopping",
                    selectedIcon = Icons.Filled.ShoppingCart,
                    unselectedIcon = Icons.Outlined.ShoppingCart,
                    route = Screen.Shopping.route
                ),
                NavBarItem(
                    title = "Home",
                    selectedIcon = Icons.Filled.PlayArrow,
                    unselectedIcon = Icons.Outlined.PlayArrow,
                    route = Screen.Skills.route
                ),
                NavBarItem(
                    title = "Profile",
                    selectedIcon = Icons.Filled.AccountCircle,
                    unselectedIcon = Icons.Outlined.AccountCircle,
                    route = Screen.Profile.route
                ),
            )

            var selectedItemIndex by rememberSaveable { mutableIntStateOf(1) }

            Scaffold(
                bottomBar = {
                    CustomNavBar(
                        items = items,
                        selectedItemIndex = selectedItemIndex,
                        onItemSelected = { index ->
                            selectedItemIndex = index
                            navController.navigate(items[index].route)
                        }
                    )
                }
            ) { innerPadding ->
                NavHost(
                    navController = navController,
                    startDestination = Screen.Skills.route,
                    modifier = Modifier.padding(innerPadding)
                ) {
                    composable(Screen.Skills.route) { SkillsScreen(viewModel, navController) }
                    composable(Screen.Profile.route) { ProfileScreen(viewModel, navController) }
                    composable(Screen.Shopping.route) { ShoppingScreen(viewModel) }
                }
            }
        }
    }
}


data class NavBarItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val route: String
)

@Composable
fun CustomNavBar(
    items: List<NavBarItem>,
    selectedItemIndex: Int,
    onItemSelected: (Int) -> Unit
) {
    NavigationBar(
        modifier = Modifier.height(48.dp) // Adjust height as needed
    ) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedItemIndex == index,
                onClick = { onItemSelected(index) },
                icon = {
                    if (index == 1) { // Customization for the middle icon (or any index you choose)
                            Icon(
                                imageVector = item.selectedIcon,
                                contentDescription = item.title,
                                modifier = Modifier.size(48.dp) // Adjust the icon size
                            )
                    } else {
                        Icon(
                            imageVector = item.selectedIcon,
                            contentDescription = item.title,
                            modifier = Modifier.size(24.dp) // Standard size for other icons
                        )
                    }
                }
            )
        }
    }
}