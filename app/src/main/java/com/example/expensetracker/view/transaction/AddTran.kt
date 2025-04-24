
package com.example.expensetracker.view.transaction

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
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
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.CardDefaults
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.platform.LocalFocusManager
import com.example.expensetracker.repository.Routes


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddTran(navController: NavController, viewModel: TransactionViewModel) {


    val focusManager = LocalFocusManager.current
    var selectedCategory by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    var transactionType by remember { mutableStateOf("EXPENSE") }
    val focusState = remember { FocusRequester() }
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    val focusRequester = remember { FocusRequester() }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F6FA))
            .verticalScroll(rememberScrollState())
    ) {
        // Header Section
        Card(
            shape = RoundedCornerShape(40.dp),

            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF1A180A)
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 45.dp),
//                horizontalArrangement = Arrangement.SpaceBetween,

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
                    "Add Transaction",

                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(start = 80.dp),
                    color = Color.White
                )
            }
            Spacer(modifier = Modifier.height(30.dp))

            // Transaction Type Selector
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                TransactionTypeButton(
                    text = "EXPENSE",
                    isSelected = transactionType == "EXPENSE",
                    color = Color(0xFFF44336),
                    onClick = {
                        transactionType = "EXPENSE"
                    }
                )

                TransactionTypeButton(
                    text = "INCOME",
                    isSelected = transactionType == "INCOME",
                    color = Color(0xFF4CAF50),
                    onClick = {
                        transactionType = "INCOME"
                    }
                )
            }
        }



        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 26.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            TextField(
                value = amount,
                onValueChange = { amount = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 100.dp)
                    .focusRequester(focusRequester),
                textStyle = LocalTextStyle.current.copy(
                    textAlign = TextAlign.Center,
                    fontSize = 24.sp,
                    color = Color.Black
                ),
                placeholder = {
                    if (amount.isEmpty() && !isFocused) {
                        Text(
                            "0",
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            color = Color.Gray.copy(alpha = 0.6f)
                        )
                    }
                },
                trailingIcon = {
                    Text(
                        "DZD",
                        color = Color.Black,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = Color(0xFF040401),
                    unfocusedTextColor = Color(0xFF040401),
                    focusedBorderColor = Color(0xFF040401),
                    unfocusedBorderColor = Color.DarkGray,
                    focusedLabelColor = Color(0xFF040401),
                    unfocusedLabelColor = Color(0xFF040401),
                    cursorColor = Color(0xFF040401)
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = { focusManager.clearFocus() }
                ),
                singleLine = true,
                interactionSource = interactionSource
            )
        }


        // Category Selection
        if (transactionType == "EXPENSE") {
            Text(
                "Categories",
                modifier = Modifier.padding(start = 16.dp, top = 8.dp),
                style = MaterialTheme.typography.bodyMedium,
                color = Color.DarkGray
            )

            CategoryGrid(
                categories = categories,
                selectedCategory = selectedCategory,
                onCategorySelected = { selectedCategory = it
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
        }


        // Date Selection
        DateSelector(
            selectedDate = selectedDate,
            onDateSelected = { newDate -> selectedDate = newDate }
        )

        // Save Button
        Button(
            onClick = {
                focusManager.clearFocus() // إخفاء لوحة المفاتيح

                val amountValue = amount.toDoubleOrNull() ?: return@Button
                viewModel.addTransaction(
                    amount = amountValue,
                    isExpense = transactionType == "EXPENSE",
                    category = selectedCategory,
                    date = selectedDate
                )
//                navController.popBackStack()
                navController.navigate(Routes.MAIN_SCREEN) {
                    // هذه الإعدادات تضمان إعادة إنشاء HomeScreen
                    launchSingleTop = true
                    restoreState = true
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(126.dp),
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