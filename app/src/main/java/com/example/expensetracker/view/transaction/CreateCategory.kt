package com.example.expensetracker.view.transaction

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.material3.CardDefaults
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.expensetracker.data.coloroptions
import com.example.expensetracker.data.iconss

@Composable
fun CreateCategoryScreen(navController: NavController) {
    val viewModel: CategoryViewModel = viewModel()
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xCD0C0C0C))
            .clickable { focusManager.clearFocus() }

    ) {
        // Header
        Card(
            shape = RoundedCornerShape(40.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF1A180A)
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(90.dp),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 35.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier.padding(start = 16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBackIosNew,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
                Text(
                    "Create Category",

                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(start = 80.dp),
                    color = Color.White
                )
            }
        }

        // Category Name
        TextField(
            value = viewModel.categoryName,
            onValueChange = { viewModel.updateCategoryName(it) },

            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 10.dp),
            textStyle = LocalTextStyle.current.copy(
                textAlign = TextAlign.Start,
                fontSize = 16.sp,
                color = Color.Black),
            placeholder = {
                if (viewModel.categoryName.isEmpty()) {
                    Text(
                        "Category Name",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Start,
                        color = Color.Gray.copy(alpha = 0.6f)
                    )
                }
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color(0xFF040401),
                unfocusedTextColor = Color(0xFF040401),
                focusedBorderColor = Color(0xFF040401),
                unfocusedBorderColor = Color.DarkGray,
                focusedLabelColor = Color(0xFF040401),
                unfocusedLabelColor = Color(0xFF040401),
                cursorColor = Color(0xFF040401)
            )


        )
        Spacer(modifier = Modifier.height(30.dp))

        // Type Selection
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.fillMaxWidth()
        ) {
            RadioButton(
                selected = viewModel.categoryType == CategoryType.EXPENSE,
                onClick = { viewModel.updateCategoryType(CategoryType.EXPENSE) },
                colors = RadioButtonDefaults.colors(
                    Color(0xFFF44336)
                )

            )
            Text("Expense")

            RadioButton(
                selected = viewModel.categoryType == CategoryType.INCOME,
                onClick = { viewModel.updateCategoryType(CategoryType.INCOME) },
                colors = RadioButtonDefaults.colors(
                    Color(0xFF4CAF50)
                )
            )
            Text("Income")
        }
        Spacer(modifier = Modifier.height(30.dp))


        // Icons Grid
        LazyVerticalGrid(
            columns = GridCells.Fixed(4),
            modifier = Modifier.height(200.dp)
        ) {
            items(iconss) { iconRes ->
                IconSelectionItem(
                    iconRes = iconRes,
                    isSelected = viewModel.selectedIcon == iconRes,
                    onSelect = { viewModel.selectIcon(iconRes) }
                )
            }
        }

        // Color Selection
        LazyVerticalGrid(
            columns = GridCells.Fixed(6),
            modifier = Modifier.height(100.dp)
        ) {
            items(coloroptions) { color ->
                ColorSelectionItem(
                    color = color,
                    isSelected = viewModel.selectedColor == color,
                    onSelect = { viewModel.selectColor(color) }
                )
            }
        }

        // Add Button
        Button(
            onClick = {
                viewModel.addCategory()
                navController.popBackStack()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(126.dp)
            ,
            shape = RoundedCornerShape(20.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFFBB0C),
                contentColor = Color.Black
            )
        ) {
            Text("Add", fontSize = 16.sp)
        }

    }
}