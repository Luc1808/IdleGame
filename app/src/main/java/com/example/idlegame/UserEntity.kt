package com.example.idlegame

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class UserEntity(
    @PrimaryKey val id: String,  // user.uid from Firebase
    val name: String,
    val email: String,
    val level: Int = 1,
)
