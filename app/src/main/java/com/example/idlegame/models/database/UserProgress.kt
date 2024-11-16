package com.example.idlegame.models.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserProgress(
    @PrimaryKey val id: Int = 0,
    val exp: Int,
    val expToLvlUp: Int,
    val lvl: Int
)
