package com.example.idlegame.models.database

// File: database/AppDatabase.kt
import Converters
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.idlegame.dao.ButtonDataDao
import com.example.idlegame.dao.ShopItemDao

@Database(entities = [ButtonData::class, ShopItem::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun buttonDataDao(): ButtonDataDao
    abstract fun shopItemDao(): ShopItemDao
}
