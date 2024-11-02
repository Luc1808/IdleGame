package com.example.idlegame

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class SkillsViewModel: ViewModel() {

    private var _exp by mutableIntStateOf(0)
    private var exp by mutableIntStateOf(_exp)

    private var _expToLvlUp by mutableIntStateOf(5)

    private var _lvl by mutableIntStateOf(1)
    var lvl by mutableIntStateOf(_lvl)

    fun addExp(addedExp: Int) {
        _exp += addedExp

        if (_exp >= _expToLvlUp) {
            lvlUp()
        }
    }

    private fun lvlUp() {
        _expToLvlUp *= 2
        lvl++
        exp = 0
    }
}