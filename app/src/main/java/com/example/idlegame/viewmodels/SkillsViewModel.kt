package com.example.idlegame.viewmodels

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.idlegame.dao.ButtonDataDao
import com.example.idlegame.models.ui.ButtonDataUiModel
import com.example.idlegame.ui.theme.btnLvl1
import com.example.idlegame.ui.theme.btnLvl2
import com.example.idlegame.utils.toUiModel
import kotlinx.coroutines.launch

class SkillsViewModel(private val buttonDataDao: ButtonDataDao): ViewModel() {

    val buttonList = mutableStateListOf<ButtonDataUiModel>()

    init {
        viewModelScope.launch {
           loadButtons()
        }
    }

    private suspend fun loadButtons() {
        // Retrieve all buttons from the database
        val buttons = buttonDataDao.getAllButtons()

        // Clear the UI list and populate it with transformed ButtonDataUiModel instances
        buttonList.clear()
        buttonList.addAll(buttons.map { it.toUiModel() })  // Here we use `toUiModel`
    }

//    val buttonList = mutableStateListOf(
//        ButtonData("1", 100, btnLvl1, 1) { addExp(1) },
//        ButtonData("2", 500, btnLvl1, 2) { addExp(2) },
//        ButtonData("3", 1100, btnLvl1, 3) { addExp(3) },
//        ButtonData("4", 1500, btnLvl1, 4) { addExp(4) },
//    )

    private val shopItemList = mutableStateListOf(
        ShopItem("1", "Double the EXP per click",Icons.Filled.Build, "DoubleEXP",buttonList[0], 2),
        ShopItem("2", "Triple the EXP per click",Icons.Filled.Build, "TripleEXP",buttonList[1], 3),
        ShopItem("3", "Cut cooldown in half", Icons.Filled.Build, "HalfCD",buttonList[3], 4),
    )

    val shopItemsUnlocked = mutableStateListOf<ShopItem>()
    private val deletedShopItems = mutableSetOf<String>()


    private var _exp by mutableIntStateOf(0)
    val exp: Int get() = _exp

    private var _expToLvlUp by mutableIntStateOf(5)
    val expToLvlUp: Int get() = _expToLvlUp

    private var _lvl by mutableIntStateOf(1)
    val lvl: Int get() = _lvl

    private fun addExp(multiplier: Int ) {
        _exp += multiplier

        if (_exp >= _expToLvlUp) {
            lvlUp()
        }
    }

    private fun lvlUp() {
        _expToLvlUp = (_expToLvlUp * 1.25).toInt()
        _lvl++
        _exp = 0
        unlockShopItem()
    }

    private fun unlockShopItem() {
        val newItems = shopItemList.filter { it.lvlUnlocked <= _lvl && !shopItemsUnlocked.contains(it) && !deletedShopItems.contains(it.name) }
        shopItemsUnlocked.addAll(newItems)
    }

    fun buttonLvlUp(shopItem: ShopItem) { // First just delete the shopItem for test
        shopItemsUnlocked.remove(shopItem)
        deletedShopItems.add(shopItem.name)

        // Find the button and apply changes directly
        val index = buttonList.indexOf(shopItem.button)
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

            // Update the onClick to apply the new multiplier
            currentButton.onClick = {
                addExp(currentButton.multiplier)
            }

            // Ensure buttonList updates with the modified button
            buttonList[index] = currentButton
        }
    }
}

data class ShopItem(
    val name: String,
    val description: String,
    val image: ImageVector,
    val buff: String,
    val button: ButtonData,
    val lvlUnlocked: Int
    )

data class ButtonData(
    val title: String,
    var coolDownTime: Long,
    var color: Color,
    var multiplier: Int,
    var onClick: () -> Unit,
) {
    var observableCoolDownTime by mutableStateOf(coolDownTime)
    var observableColor by mutableStateOf(color)
}
