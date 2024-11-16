package com.example.idlegame.models.database

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class ShopItem(
    @PrimaryKey val id: String,
    val name: String,
    val description: String,
    val buff: String,
    val buttonID: Int,
    val baseLvlUnlocked: Int, // Base level for the first unlock
    val levelMultiplier: Int // Multiplier for subsequent unlocks
)