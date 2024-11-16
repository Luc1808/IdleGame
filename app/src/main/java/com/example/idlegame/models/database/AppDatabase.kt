package com.example.idlegame.models.database

// File: database/AppDatabase.kt
import android.content.Context
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory.Companion.instance
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.idlegame.dao.ButtonDataDao
import com.example.idlegame.dao.ShopItemDao
import com.example.idlegame.dao.UserProgressDao
import com.example.idlegame.ui.theme.btnLvl1
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [ButtonData::class, ShopItem::class, UserProgress::class], version = 12)
abstract class AppDatabase : RoomDatabase() {
        abstract fun buttonDataDao(): ButtonDataDao
        abstract fun shopItemDao(): ShopItemDao
        abstract fun userProgressDao(): UserProgressDao



    companion object {

        // Initial data definitions
        val initialButtons = listOf(
            ButtonData(title = "1", coolDownTime = 100, color = btnLvl1, multiplier = 1),
            ButtonData(title = "2", coolDownTime = 500, color = btnLvl1, multiplier = 2),
            ButtonData(title = "3", coolDownTime = 1100, color = btnLvl1, multiplier = 3),
            ButtonData(title = "4", coolDownTime = 1500, color = btnLvl1, multiplier = 4),
        )

        val initialShopItems = listOf(
            ShopItem("1",  initialButtons[0].title,"Double the EXP per click", "DoubleEXP", 1, 2, 2),
            ShopItem("2", initialButtons[1].title, "Triple the EXP per click", "TripleEXP", 2, 3, 2),
            ShopItem("3", initialButtons[3].title, "Cut cooldown in half", "HalfCD", 4, 4, 2)
        )
        // Volatile ensures that instance changes are visible across threads
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "idlegame-db"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(createDatabaseWithCallback())
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private fun createDatabaseWithCallback() = object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                println("onCreate: Database is being created. Inserting initial data...")
                CoroutineScope(Dispatchers.IO).launch {
                    INSTANCE?.let { database ->
                        database.buttonDataDao().insertAll(initialButtons)
                        database.shopItemDao().insertAll(initialShopItems)
                        println("onCreate: Initial data inserted.")
                    }
                }
            }
        }
    }
}
