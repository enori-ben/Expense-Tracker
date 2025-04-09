package com.example.expensetracker.view.transaction

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun IconSelectionItem(
    iconRes: Int,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    val imageSize = 65.dp
    Box(
        modifier = Modifier
            .padding(23.dp)
            .width(80.dp)
            .clickable(onClick = onSelect)
            .background(
                color = if (isSelected) Color.LightGray else Color.Transparent,
                shape = RoundedCornerShape(6.dp)
            )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = null,
            modifier = Modifier
                .padding(8.dp)
                .size(40.dp)
        )
    }
        }
}

@Composable
fun ColorSelectionItem(
    color: Color,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    Box(
        modifier = Modifier
            .padding(4.dp)
            .size(40.dp)
            .clip(CircleShape)
            .clickable(onClick = onSelect)
            .background(color)
            .border(
                width = if (isSelected) 3.dp else 0.dp,
                color = Color.Black,
                shape = CircleShape
            )
    )
}