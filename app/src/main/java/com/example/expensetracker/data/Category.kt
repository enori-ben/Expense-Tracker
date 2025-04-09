package com.example.expensetracker.data

import com.example.expensetracker.R

data class Category(
    val name: String,
    val iconRes: Int,
)

val categories = listOf(
    Category("Health", R.drawable.ic_health),
    Category("Food", R.drawable.ic_food),
    Category("Workout", R.drawable.gym),
    Category("Apparel", R.drawable.ic_apparal),
    Category("Education", R.drawable.ic_edu),
    Category("Gifts", R.drawable.ic_gifts),
    Category("Transport", R.drawable.ic_transport),
    Category("Create", R.drawable.plus),
    Category("Education", R.drawable.ic_edu),
    Category("Gifts", R.drawable.ic_gifts),
    Category("Transport", R.drawable.ic_transport),
    Category("Create", R.drawable.plus)

)


