package com.example.idlegame.viewmodels

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ViewModel
import com.example.idlegame.ui.theme.btnLvl1

class SkillsViewModel: ViewModel() {

    val buttonList = listOf(
        ButtonData("1", 100, btnLvl1) { addExp(1) },
        ButtonData("2", 500, btnLvl1) { addExp(2) },
        ButtonData("3", 1100, btnLvl1) { addExp(3) },
        ButtonData("4", 1500, btnLvl1) { addExp(4) },
    )

    val shopItemList = listOf(
        ShopItem("1", "Enhance the button in double",Icons.Filled.Build, buttonList[0], 4),
        ShopItem("2", "Crazy things happens here",Icons.Filled.Build, buttonList[1], 5),
        ShopItem("3", "I dont even know what else to say", Icons.Filled.Build, buttonList[3], 6),
    )

    val shopItemsUnlocked = mutableListOf<ShopItem>()

    private var _exp by mutableIntStateOf(0)
    val exp: Int get() = _exp

    private var _expToLvlUp by mutableIntStateOf(5)
    val expToLvlUp: Int get() = _expToLvlUp

    private var _lvl by mutableIntStateOf(1)
    val lvl: Int get() = _lvl

    private fun addExp(addedExp: Int) {
        _exp += addedExp

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
        val newItems = shopItemList.filter { it.lvlUnlocked <= _lvl && !shopItemsUnlocked.contains(it) }
        shopItemsUnlocked.addAll(newItems)
    }

    fun buttonLvlUp(shopItem: ShopItem) { // First just delete the shopItem for test
        shopItemsUnlocked.remove(shopItem)
    }
}

data class ShopItem(
    val name: String,
    val description: String,
    val image: ImageVector,
    val button: ButtonData,
    val lvlUnlocked: Int
    )

data class ButtonData(
    val title: String,
    val coolDownTime: Long,
    val color: Color,
    val onClick: () -> Unit,
)
