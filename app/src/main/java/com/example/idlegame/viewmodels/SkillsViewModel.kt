package com.example.idlegame.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.idlegame.dao.ButtonDataDao
import com.example.idlegame.dao.ShopItemDao
import com.example.idlegame.dao.UserProgressDao
import com.example.idlegame.models.database.ButtonData
import com.example.idlegame.models.database.ShopItem
import com.example.idlegame.models.database.UserProgress
import com.example.idlegame.ui.theme.btnLvl2
import kotlinx.coroutines.launch

class SkillsViewModel(private val buttonDataDao: ButtonDataDao, private val shopItemDao: ShopItemDao, private val userProgressDao: UserProgressDao): ViewModel() {

    val buttonList = mutableStateListOf<ButtonData>()
//    val shopItemList = mutableStateListOf<ShopItem>()
    val shopItemsUnlocked =  SnapshotStateList<ShopItem>()

    init {
        viewModelScope.launch {
            loadProgress()
           loadButtons()
        }
    }


    private suspend fun loadButtons() {
        // Retrieve all buttons from the database
        val buttons = buttonDataDao.getAllButtons()
//        val shopItems = shopItemDao.getAllShopItems()

        println("Loaded Buttons from Database: $buttons")
//        println("Loaded ShopItems from Database: $shopItems")

        // Clear the UI list and populate it with transformed ButtonDataUiModel instances
        buttonList.clear()
        shopItemsUnlocked.clear()
        buttonList.addAll(buttons)
//        shopItemsUnlocked.addAll(shopItems)
    }


//    private val deletedShopItems = mutableSetOf<String>()


    private var _exp by mutableIntStateOf(0)
    val exp: Int get() = _exp

    private var _expToLvlUp by mutableIntStateOf(5)
    val expToLvlUp: Int get() = _expToLvlUp

    private var _lvl by mutableIntStateOf(1)
    val lvl: Int get() = _lvl

    private suspend fun loadProgress() {
        val progress = userProgressDao.getProgress()
        if (progress != null) {
            _exp = progress.exp
            _expToLvlUp = progress.expToLvlUp
            _lvl = progress.lvl
        }
    }

    private fun saveProgress() {
        viewModelScope.launch {
            val progress = UserProgress(
                exp = _exp,
                expToLvlUp = _expToLvlUp,
                lvl = _lvl
            )
            userProgressDao.insertProgress(progress)
        }
    }

    fun addExp(multiplier: Int) {
        _exp += multiplier
        saveProgress()

        if (_exp >= _expToLvlUp) {
            lvlUp()
        }
    }

    private fun lvlUp() {
        _expToLvlUp = (_expToLvlUp * 1.25).toInt()
        _lvl++
        _exp = 0
        saveProgress()
        unlockShopItem()
    }

    private fun unlockShopItem() {
        // Check if there are any existing items to unlock
        viewModelScope.launch {
            val newItems = shopItemDao.getShopItems(_lvl)
                .filterNotNull()
            shopItemsUnlocked.addAll(newItems)
            shopItemDao.deleteShopItems(shopItemsUnlocked)
        }
//        val newItems = shopItemsUnlocked.filter {
//            it.baseLvlUnlocked <= _lvl && !shopItemsUnlocked.contains(it)
//        }
//        shopItemsUnlocked.addAll(newItems)
    }

    fun buyShopItem(shopItem: ShopItem) {

        viewModelScope.launch {
            // Find the button and apply changes directly
            val button = buttonList.find { it.id == shopItem.buttonID }
            val index = buttonList.indexOf(button)
            if (index != -1) {
                val currentButton = buttonList[index]

                // Apply buff based on the type
                when (shopItem.buff) {
                    "DoubleEXP" -> {
                        currentButton.multiplier *= 2
                        currentButton.color = btnLvl2
                    }

                    "TripleEXP" -> {
                        currentButton.multiplier *= 3
                        currentButton.color = btnLvl2
                    }

                    "HalfCD" -> {
                        currentButton.coolDownTime /= 2
                        currentButton.color = btnLvl2
                    }
                }

                addExp(currentButton.multiplier)

                // Update the buttonList
                buttonList[index] = currentButton

                // Save changes to the database
                buttonDataDao.updateButton(currentButton)

                // Create the next ShopItem
                createNextShopItem(shopItem)
            }
        }
    }


    private fun createNextShopItem(previousItem: ShopItem) {
        shopItemsUnlocked.remove(previousItem)

        val nextLvlUnlocked = previousItem.baseLvlUnlocked * previousItem.levelMultiplier
        val nextShopItem = ShopItem(
            id = "${previousItem.id}_${nextLvlUnlocked}",
            name = previousItem.name,
            description = previousItem.description,
            buff = previousItem.buff,
            buttonID = previousItem.buttonID,
            baseLvlUnlocked = nextLvlUnlocked,
            levelMultiplier = previousItem.levelMultiplier
        )

        // Add the new ShopItem to the database and the list
        viewModelScope.launch {
            shopItemDao.deleteShopItem(previousItem)
            shopItemDao.insertShopItem(nextShopItem)
            unlockShopItem()
        }
    }

}

