package com.example.idlegame.models.database

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class ButtonData(
    @PrimaryKey (autoGenerate = true) val id: Int = 0,
    val title: String,
    var coolDownTime: Long,
    var color: String,
    var multiplier: Int,
)