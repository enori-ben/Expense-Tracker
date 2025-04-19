package com.example.expensetracker.view.transaction

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.expensetracker.data.Category
import androidx.compose.ui.draw.clip

@Composable
fun CategoryItem(
    category: Category,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val imageSize = 65.dp // حجم الصورة ثابت في كلتا الحالتين
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(8.dp)
            .width(80.dp) // اضبطها حسب الحاجة
    ) {
        if (isSelected) {
            // الحالة المختارة: عرض مربع يحتوي على الصورة والنص معًا
            Box(
                modifier = Modifier
                    .size(95.dp)
                    .clickable { onClick() }
                    .background(
                        color = Color.LightGray,
                        shape = RoundedCornerShape(10.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = category.iconRes),
                        contentDescription = category.name,
                        modifier = Modifier.size(imageSize)
                    )

                    Text(
                        text = category.name,
                        color = Color.Black,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
            }
        } else {
            // الحالة الافتراضية: عرض دائرة تحتوي على الصورة والنص تحتها
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .clickable { onClick() }
                    .background(
                        color = Color.LightGray,
                    ),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = category.iconRes),
                    contentDescription = category.name,
                    modifier = Modifier.size(imageSize)
                )
            }
            Text(
                text = category.name,
                fontSize = 10.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Gray
            )
        }
    }
}
