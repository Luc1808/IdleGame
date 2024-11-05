package com.example.idlegame.views

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.idlegame.viewmodels.SkillsViewModel
import kotlinx.coroutines.launch


@Composable
fun SkillsScreen(viewModel: SkillsViewModel, navController: NavHostController) {

    val buttonList = viewModel.buttonList

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

//        SkillButton(title = "1", onClick = { viewModel.addExp(1) }, coolDownTime = 100, btnLvl1)
//        SkillButton(title = "2", onClick = { viewModel.addExp(2) }, coolDownTime = 500, btnLvl1)
//        SkillButton(title = "3", onClick = { viewModel.addExp(3) }, coolDownTime = 1100, btnLvl1)
//        SkillButton(title = "4", onClick = { viewModel.addExp(4) }, coolDownTime = 1500, btnLvl1)

        LazyColumn(
            verticalArrangement = spacedBy(16.dp)
        ) {
            items(buttonList) { buttonData ->
                SkillButton(
                    title = buttonData.title,
                    onClick = { buttonData.onClick() },
                    coolDownTime = buttonData.coolDownTime,
                    color = buttonData.color
                )
            }
        }
    }
}


@Composable
fun SkillButton(title: String, onClick: () -> Unit, coolDownTime: Long, color: Color) {

    var isButtonEnabled by remember { mutableStateOf(true) }
    val scope = rememberCoroutineScope()
    val progress = remember { Animatable(0f) }

    Button(
        onClick = {
            if (isButtonEnabled) {
                onClick()
                isButtonEnabled = false
                scope.launch {
                    progress.animateTo(
                        targetValue = 1f,
                        animationSpec = tween(coolDownTime.toInt())
                    )
                   isButtonEnabled = true
                   progress.snapTo(0f)
                }
            }
        },
        enabled = isButtonEnabled,
        contentPadding = PaddingValues(0.dp), // Remove internal padding
        colors =
            ButtonDefaults.buttonColors(
                containerColor = color,
                contentColor = Color(0xFFFFFFFF),
                disabledContainerColor = Color(0xFF869a9b),
                disabledContentColor = Color(0xFF000000)
            )
            ,
        modifier = Modifier
            .width(370.dp)
            .height(70.dp),
        shape = RoundedCornerShape(10.dp),
    ) {
        Box {
            Canvas(
                modifier = Modifier
                    .fillMaxSize() // Ensure Canvas fills the Box completely
            ) {
                // Draw the rectangle to represent the progress bar
                drawRect(
                    color = color,
                    topLeft = Offset(0f, 0f), // Start from the top-left corner
                    size = this.size.copy(width = progress.value * this.size.width)
                )
            }
            // Place the title in the center of the button
            Text(
                text = title,
                modifier = Modifier.align(Alignment.Center),
                color = Color.White
            )
        }
    }
}