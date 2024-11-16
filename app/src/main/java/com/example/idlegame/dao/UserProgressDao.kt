package com.example.idlegame.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.idlegame.models.database.UserProgress

@Dao
interface UserProgressDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProgress(progress: UserProgress)

    @Query("SELECT * FROM UserProgress WHERE id = 0")
    suspend fun getProgress(): UserProgress?
}
