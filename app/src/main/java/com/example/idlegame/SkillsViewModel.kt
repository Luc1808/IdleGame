package com.example.idlegame

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class SkillsViewModel: ViewModel() {

    private var _exp by mutableIntStateOf(0)
    val exp: Int get() = _exp

    private var _expToLvlUp by mutableIntStateOf(5)
    val expToLvlUp: Int get() = _expToLvlUp

    private var _lvl by mutableIntStateOf(1)
    val lvl: Int get() = _lvl

    fun addExp(addedExp: Int) {
        _exp += addedExp

        if (_exp >= _expToLvlUp) {
            lvlUp()
        }
    }

    private fun lvlUp() {
        _expToLvlUp = (_expToLvlUp * 1.25).toInt()
        _lvl++
        _exp = 0
    }
}