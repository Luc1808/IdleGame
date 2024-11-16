package com.example.idlegame.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.idlegame.models.database.ShopItem

@Dao
interface ShopItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShopItem(shopItem: ShopItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(shopItems: List<ShopItem>)

    @Query("SELECT * FROM ShopItem WHERE baseLvlUnlocked <= :lvl")
    suspend fun getShopItems(lvl: Int): List<ShopItem?>

    @Query("SELECT * FROM ShopItem")
    suspend fun getAllShopItems(): List<ShopItem>

    @Update
    suspend fun updateShopItem(shopItem: ShopItem)

    @Delete
    suspend fun deleteShopItem(shopItem: ShopItem)

    @Delete
    suspend fun deleteShopItems(list: List<ShopItem>)
}
