package com.example.idlegame.models.ui

import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.Color

data class ButtonDataUiModel(
    val id: String,
    val title: String,
    val coolDownTime: MutableState<Long>,
    val color: MutableState<Color>,
    val multiplier: MutableState<Int>,
    val onClickExp: Int
)
