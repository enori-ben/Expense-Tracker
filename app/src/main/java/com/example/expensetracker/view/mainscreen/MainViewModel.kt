package com.example.expensetracker.view.mainscreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    var selectedIndex by mutableIntStateOf(0)
        private set

    fun updateIndex(index: Int) {
        selectedIndex = index
    }
}