package com.example.idlegame.utils

// File: utils/ButtonDataExtensions.kt

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import com.example.idlegame.models.database.ButtonData
import com.example.idlegame.models.ui.ButtonDataUiModel
import com.example.idlegame.ui.theme.btnLvl1

fun ButtonData.toUiModel() = ButtonDataUiModel(
    id = id,
    title = title,
    coolDownTime = mutableLongStateOf(coolDownTime),
    color = mutableStateOf(btnLvl1),  // Use an appropriate color here
    multiplier = mutableIntStateOf(multiplier),
    onClickExp = onClickExp
)
