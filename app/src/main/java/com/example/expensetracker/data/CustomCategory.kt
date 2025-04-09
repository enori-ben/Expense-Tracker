package com.example.expensetracker.data

import androidx.compose.ui.graphics.Color
import com.example.expensetracker.view.transaction.CategoryType

data class CustomCategory(
    val id: String = "", // يمكنك استخدام UUID.randomUUID().toString() لإنشاء ID
    val name: String,
    val type: CategoryType, // النوع (مصروف أو دخل)
    val iconRes: Int, // أيقونة الفئة
    val color: Color // لون الفئة
)

enum class CategoryType {
    EXPENSE,
    INCOME
}