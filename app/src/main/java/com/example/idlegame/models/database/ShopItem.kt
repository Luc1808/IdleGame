package com.example.idlegame.models.database

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class ShopItem(
    @PrimaryKey val id: String,
    val name: String,
    val description: String,
    val buff: String,
    val buttonId: String,
    val lvlUnlocked: Int
)