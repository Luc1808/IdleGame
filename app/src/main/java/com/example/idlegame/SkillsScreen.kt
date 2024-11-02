package com.example.idlegame

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun SkillsScreen(viewModel: SkillsViewModel) {

    val lvl = viewModel.lvl

    Text(text = "Level: $lvl", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
       Row(
           modifier = Modifier.fillMaxWidth(),
           horizontalArrangement = Arrangement.SpaceEvenly,
       ) {
           Button(onClick = { viewModel.addExp(1) }, shape = CircleShape, modifier = Modifier.size(100.dp)) {
                Text(text = "1")
           }

           Button(onClick = { viewModel.addExp(2) }, shape = CircleShape, modifier = Modifier.size(100.dp)) {
               Text(text = "2")
           }
       }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            Button(onClick = { viewModel.addExp(3) }, shape = CircleShape, modifier = Modifier.size(100.dp)) {
                Text(text = "3")
            }

            Button(onClick = { viewModel.addExp(4) }, shape = CircleShape, modifier = Modifier.size(100.dp)) {
                Text(text = "4")
            }
        }
    }
}