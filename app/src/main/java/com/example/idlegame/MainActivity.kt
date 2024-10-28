package com.example.idlegame

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.example.idlegame.ui.theme.IdleGameTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

private const val TAG = "MainActivity"

class MainActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var userRepository: UserRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
//        Log.i(TAG, "onCreate: ${auth.currentUser}")

        val userDao = UserDatabase.getDatabase(this).userDao()
        userRepository = UserRepository(userDao)

        setContent {
            IdleGameTheme {
                Navigation(auth, userRepository)
            }
        }
    }
}


