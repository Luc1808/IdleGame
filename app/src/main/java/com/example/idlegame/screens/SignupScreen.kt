package com.example.idlegame.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.idlegame.UserEntity
import com.example.idlegame.UserRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@Composable
fun SignupScreen(navController: NavController, auth: FirebaseAuth, userRepository: UserRepository) {

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        OutlinedTextField(value = name, onValueChange = { name = it })
        Spacer(modifier = Modifier.padding(8.dp))
        OutlinedTextField(value = email, onValueChange = { email = it })
        Spacer(modifier = Modifier.padding(8.dp))
        OutlinedTextField(value = password, onValueChange = { password = it })
        Spacer(modifier = Modifier.padding(8.dp))


        Button(onClick = {
            if (email.isNotEmpty() && password.isNotEmpty()) {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val user = auth.currentUser

                            user?.let {
                                val userEntity = UserEntity(
                                    id = it.uid,
                                    name = name,
                                    email = email
                                )

                                CoroutineScope(Dispatchers.IO).launch {
                                    userRepository.insertUser(userEntity)
                                }


                                Toast.makeText(context, "Sign-up successful!", Toast.LENGTH_SHORT).show()
                                navController.navigate(Screen.LoginScreen.route)

                            }

                        } else {
                            try {
                                throw task.exception ?: Exception("Unknown error")
                            } catch (e: FirebaseAuthWeakPasswordException) {
                                // Handle weak password
                                Toast.makeText(context, "Password needs to have at least 6 characters", Toast.LENGTH_SHORT).show()
                                // Inform the user with an appropriate message
                            } catch (e: Exception) {
                                Log.e("SignUp", "Sign-up failed: ${e.message}")
                            }
                        }
                    }
            } else {

                Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }) {
            Text(text = "SIGN UP")
        }
        TextButton(onClick = {
            navController.navigate(Screen.LoginScreen.route)
        }) {
            Text(text = "Do you have an account already? Log in!")
        }
    }
}


@Preview
@Composable
fun SignupScreenPreview() {
//    SignupScreen()
}