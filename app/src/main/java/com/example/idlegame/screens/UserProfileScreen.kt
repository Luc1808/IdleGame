package com.example.idlegame.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.idlegame.UserEntity
import com.example.idlegame.UserRepository
import com.google.firebase.auth.FirebaseAuth

@Composable
fun UserProfileScreen(navController: NavController, auth: FirebaseAuth, userRepository: UserRepository)  {
    val user = auth.currentUser
    val context = LocalContext.current
    var userEntity by remember { mutableStateOf<UserEntity?>(null) }

    LaunchedEffect(user) {
        if (user != null) {
            // Fetch user data from the database
            userEntity = userRepository.getUser(user.uid)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (user != null) {
            // Display user details
            Text(text = "User ID: ${user.uid}")
            Spacer(modifier = Modifier.padding(8.dp))
            Text(text = "Email: ${user.email ?: "No email available"}")
            Spacer(modifier = Modifier.padding(8.dp))
            Text(text = "Display Name: ${user.displayName ?: "No display name available"}")

            Spacer(modifier = Modifier.padding(16.dp))

            // Sign out button
            Button(onClick = {
                auth.signOut()
                Toast.makeText(context, "Signed out successfully!", Toast.LENGTH_SHORT).show()
                // Navigate back to the Login screen
                navController.navigate(Screen.LoginScreen.route) {
                    // Ensure the user cannot go back to the profile screen by clearing the back stack
                    popUpTo(0) { inclusive = true }
                    launchSingleTop = true
                }
            }) {
                Text(text = "Sign Out")
            }
        }
    }
}