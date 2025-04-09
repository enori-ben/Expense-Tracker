package com.example.expensetracker.view.transaction

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.example.expensetracker.R
import com.example.expensetracker.data.CustomCategory

enum class CategoryType { EXPENSE, INCOME }

class CategoryViewModel : ViewModel() {
    var categoryName by mutableStateOf("")
    var categoryType by mutableStateOf(CategoryType.EXPENSE)
    var selectedIcon by mutableStateOf(R.drawable.me)
    var selectedColor by mutableStateOf(Color.Gray)

    fun updateCategoryName(name: String) { categoryName = name }
    fun updateCategoryType(type: CategoryType) { categoryType = type }
    fun selectIcon(iconRes: Int) { selectedIcon = iconRes }
    fun selectColor(color: Color) { selectedColor = color }

    fun addCategory() {
        val newCategory = CustomCategory(
            name = categoryName,
            type = categoryType,
            iconRes = selectedIcon,
            color = selectedColor
        )
        // أضف منطق حفظ الفئة هنا
    }
}