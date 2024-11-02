package com.example.idlegame

import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SkillsScreen(viewModel: SkillsViewModel) {

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = spacedBy(16.dp)
    ) {
        Text(
            text = "Level: ${viewModel.lvl} (${viewModel.exp}/${viewModel.expToLvlUp})",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.padding(16.dp))

        SkillButton(title = "1", onClick = { viewModel.addExp(1) }, coolDownTime = 200)
        SkillButton(title = "2", onClick = { viewModel.addExp(2) }, coolDownTime = 500)
        SkillButton(title = "3", onClick = { viewModel.addExp(3) }, coolDownTime = 1100)
        SkillButton(title = "4", onClick = { viewModel.addExp(4) }, coolDownTime = 1500)
    }
}


@Composable
fun SkillButton(title: String, onClick: () -> Unit, coolDownTime: Long) {

    var isButtonEnabled by remember { mutableStateOf(true) }
    val scope = rememberCoroutineScope()

    Button(
        onClick = {
            if (isButtonEnabled) {
                onClick()
                isButtonEnabled = false
                scope.launch {
                   delay(coolDownTime)
                   isButtonEnabled = true
                }
            }
        },
        enabled = isButtonEnabled,
        colors =
            ButtonDefaults.buttonColors(
                containerColor = Color(0xFFa3e2e5),
                contentColor = Color(0xFFFFFFFF),
                disabledContainerColor = Color(0xFF869a9b),
                disabledContentColor = Color(0xFF000000)
            )
            ,
        modifier = Modifier
            .width(350.dp)
            .height(60.dp),
        shape = RoundedCornerShape(10.dp),
    ) {
       Text(text = title)
    }
}