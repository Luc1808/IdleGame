package com.example.idlegame.dao

import androidx.room.*
import com.example.idlegame.models.database.ButtonData
import com.example.idlegame.models.database.ShopItem

@Dao
interface ButtonDataDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertButton(button: ButtonData)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(buttons: List<ButtonData>)

    @Query("SELECT * FROM ButtonData WHERE id = :id")
    suspend fun getButton(id: String): ButtonData?

    @Update
    suspend fun updateButton(button: ButtonData)

    @Query("SELECT * FROM ButtonData")
    suspend fun getAllButtons(): List<ButtonData>
}

