package com.example.idlegame.models.database

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class ButtonData(
    @PrimaryKey val id: String,
    val title: String,
    var coolDownTime: Long,
    var color: Int, // Store Color as an int
    var multiplier: Int,
    var onClick: Int
)