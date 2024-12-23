package com.example.idlegame.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.idlegame.viewmodels.SkillsViewModel

@Composable
fun ProfileScreen(viewModel: SkillsViewModel, navController: NavController) {

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
       Text(text = "Profile Screen", fontSize = 30.sp)

       Spacer(modifier = Modifier.height(16.dp))

       Text(text = "Player: playername")

       Spacer(modifier = Modifier.height(16.dp))

       Text(text = "Level: ${viewModel.lvl}")
    }
}

