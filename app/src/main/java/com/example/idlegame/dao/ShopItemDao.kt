package com.example.idlegame.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.idlegame.models.database.ShopItem

@Dao
interface ShopItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShopItem(shopItem: ShopItem)

    @Query("SELECT * FROM ShopItem WHERE id = :id")
    suspend fun getShopItem(id: String): ShopItem?

    @Update
    suspend fun updateShopItem(shopItem: ShopItem)
}
