package com.example.expensetracker.view.transaction

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.expensetracker.data.categories
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.ui.platform.LocalFocusManager
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.expensetracker.data.CategoryGrid
import com.example.expensetracker.repository.Routes
import java.time.Instant
import java.time.ZoneId

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddTran(navController: NavController, viewModel: TransactionViewModel) {
    val focusManager = LocalFocusManager.current
    var selectedCategory by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    var transactionType by remember { mutableStateOf("EXPENSE") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F6FA))
            .padding(16.dp)
    ) {
        // Header Section
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBackIosNew,
                    contentDescription = "Back",
                    tint = Color.Black
                )
            }
            Text(
                text = "Add Transaction",
                style = MaterialTheme.typography.titleLarge,
                color = Color.Black
            )
            Spacer(modifier = Modifier.width(48.dp)) // Placeholder for alignment
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Amount Input
        OutlinedTextField(
            value = amount,
            onValueChange = { amount = it },
            label = { Text("Amount") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Transaction Type Selector
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            TransactionTypeButton(
                text = "Expense",
                isSelected = transactionType == "EXPENSE",
                color = Color(0xFFF44336),
                onClick = { transactionType = "EXPENSE" }
            )
            TransactionTypeButton(
                text = "Income",
                isSelected = transactionType == "INCOME",
                color = Color(0xFF4CAF50),
                onClick = { transactionType = "INCOME" }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Category Selection
        Text(
            text = "Category",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Gray
        )
        CategoryGrid(
            categories = categories,
            selectedCategory = selectedCategory,
            onCategorySelected = { selectedCategory = it }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Date Picker
        DateSelector(
            selectedDate = selectedDate,
            onDateSelected = { selectedDate = it }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Save Button
        Button(
            onClick = {
                val amountValue = amount.toDoubleOrNull() ?: return@Button
                viewModel.addTransaction(
                    amount = amountValue,
                    isExpense = transactionType == "EXPENSE",
                    category = selectedCategory,
                    date = selectedDate
                )
                navController.popBackStack()
                navController.navigate(Routes.HOME_SCREEN) {
                    launchSingleTop = true
                    restoreState = true
                }
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFBB0C))
        ) {
            Text("Save", color = Color.White)
        }
    }
}

@Composable
fun TransactionTypeButton(
    text: String,
    isSelected: Boolean,
    color: Color,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (isSelected) 8.dp else 4.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) color else Color.LightGray
        ),
        modifier = Modifier
            .width(120.dp)
            .height(50.dp)
            .clickable { onClick() }
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()

        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.titleLarge,
                color = if (isSelected) Color.Black else Color.DarkGray
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateSelector(
    selectedDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit
) {
    var showDatePicker by remember { mutableStateOf(false) }
    val dateFormatter = remember { DateTimeFormatter.ofPattern("MMM dd, yyyy") }
    val datePickerState = rememberDatePickerState()

    // Row لعرض التاريخ وزر التقويم
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 6.dp, start = 20.dp, end = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = selectedDate.format(dateFormatter),
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.bodyLarge
        )
        IconButton(
            onClick = { showDatePicker = true },
            modifier = Modifier.size(48.dp)
        ) {
            Icon(
                imageVector = Icons.Default.CalendarMonth,
                contentDescription = "Select Date",
                tint = Color(0xFF070000)
            )
        }
    }

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            colors = DatePickerDefaults.colors(
                containerColor = Color.White,
                headlineContentColor = Color.Black,
                todayContentColor = Color(0xFF070000),
                selectedDayContainerColor = Color(0xFFFFBB0C)
            ),
            confirmButton = { // أضف هذا الجزء
                Button(
                    onClick = {
                        datePickerState.selectedDateMillis?.let { millis -> 
                            val instant = Instant.ofEpochMilli(millis)
                            val newDate = instant.atZone(ZoneId.systemDefault()).toLocalDate()
                            onDateSelected(newDate)
                        }
                        showDatePicker = false
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF070000),
                        contentColor = Color.White
                    )
                ) {
                    Text("confirm")
                }
            }
        ) {
            DatePicker(
                state = datePickerState,
                title = {
                    Text(
                        "اختر التاريخ",
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier.padding(16.dp)
                    )
                },
                showModeToggle = true
            )
        }
    }
}