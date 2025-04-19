package com.example.expensetracker.view

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.savedstate.SavedStateRegistryOwner
import com.example.expensetracker.R
import com.example.expensetracker.repository.Routes
import com.example.expensetracker.view.transaction.Transaction
import com.example.expensetracker.view.transaction.TransactionViewModel
import kotlinx.coroutines.delay
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: TransactionViewModel,
    onProfileClick: () -> Unit = { navController.navigate(Routes.PROFILE_SCREEN) },
    onMenuClick: () -> Unit = {}
) {
    // State variables
    val transactions by viewModel.transactions.collectAsStateWithLifecycle()
    val totalBalance by viewModel.totalBalance.collectAsStateWithLifecycle()
    val totalIncome by viewModel.totalIncome.collectAsStateWithLifecycle()
    val totalExpense by viewModel.totalExpense.collectAsStateWithLifecycle()

    // Debugging
    LaunchedEffect(transactions) {
        println("HomeScreen - Transactions updated: ${transactions.size}")
        println("HomeScreen - Current balance: $totalBalance")
        println("HomeScreen - Total income: $totalIncome")
        println("HomeScreen - Total expense: $totalExpense")
    }

    // UI State variables
    var selectedPeriod by remember { mutableStateOf("Day") }
    var currentDate by remember { mutableStateOf(LocalDate.now()) }

    // Formatters
    val dateFormatter = remember { DateTimeFormatter.ofPattern("dd MMM yyyy") }
    val dailyFormatter = remember { DateTimeFormatter.ofPattern("EEEE, d MMM") }

    // Filtered transactions
    val filteredTransactions = remember(transactions, selectedPeriod, currentDate) {
        when (selectedPeriod) {
            "Day" -> transactions.filter { it.date == currentDate }
            "Week" -> {
                val startOfWeek = currentDate.with(java.time.DayOfWeek.MONDAY)
                val endOfWeek = startOfWeek.plusDays(6)
                transactions.filter { it.date in startOfWeek..endOfWeek }
            }

            "Month" -> transactions.filter {
                it.date.year == currentDate.year && it.date.month == currentDate.month
            }

            "Year" -> transactions.filter { it.date.year == currentDate.year }
            else -> transactions
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F6FA))

    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()

        ) {
            // Header Section

            HeaderSection(onMenuClick, onProfileClick)
            // Balance and Summary
            BalanceSummarySection(totalBalance, totalIncome, totalExpense)


            // Period Selector
            PeriodSelector(
                selectedPeriod = selectedPeriod,
                currentDate = currentDate,
                dateFormatter = dateFormatter,
                dailyFormatter = dailyFormatter,
                onPeriodSelected = { selectedPeriod = it },
                onDateChanged = { currentDate = it }
            )

            // Transactions List
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                if (filteredTransactions.isEmpty()) {
                    item {
                        Text(
                            text = "No transactions found",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),

                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color =
                                Color.Gray
                            )
                        )
                    }
                } else {
                    items(filteredTransactions.reversed()) { transaction ->
                        TransactionItem(transaction)
                    }
                }
            }





        }
        Box(modifier = Modifier.fillMaxSize().padding(top = 600.dp)){
            // Floating Action Button
            FAB(navController)
        }

    }


}

@Composable
fun FAB(navController: NavController) {

    FloatingActionButton(
        onClick = {
            navController.navigate(Routes.ADD_TRANSACTION)
        },
        modifier = Modifier.padding(24.dp)
            .shadow(
                elevation = 8.dp,
                shape = CircleShape,
                spotColor = Color.Black.copy(alpha = 0.3f)
            ),
        containerColor = Color(0xFFFFBB0C),
        shape = CircleShape,
        contentColor = Color.Black
    ) {
        Icon(
            painter = painterResource(id = R.drawable.add),
            contentDescription = "Add Transaction",
            modifier = Modifier.size(32.dp)
        )
    }




}

@Composable
private fun BalanceSummarySection(
    totalBalance: Double,
    totalIncome: Double,
    totalExpense: Double
) {
    Column(modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp)) {
        Text(
            text = "Total Balance",
            style = MaterialTheme.typography.titleMedium,
            color = Color(0xFF666666)
        )
        Text(
            text = "%.2f DZ".format(totalBalance),
            style = MaterialTheme.typography.displaySmall.copy(
                fontSize = 33.sp,
                fontWeight = FontWeight.Bold,
                color = if (totalBalance >= 0) Color(0xFF4CAF50) else Color(0xFFF44336)
            )
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IncomeExpenseItem("Income", totalIncome, Color(0xFF4CAF50))
            IncomeExpenseItem("Expense", totalExpense, Color(0xFFF44336))
        }
    }
}
@Composable
private fun IncomeExpenseItem(label: String, amount: Double, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(label, style = MaterialTheme.typography.bodyMedium)
        Text(
            "%.2f DZ".format(amount),
            color = color,
            style = MaterialTheme.typography.titleLarge
        )
    }
}

// Sub-Composables
@Composable
private fun HeaderSection(
    onMenuClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    Spacer(modifier = Modifier.height(16.dp))

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(23.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onMenuClick) {
            Icon(
                painter = painterResource(R.drawable.menu),
                contentDescription = "Menu",
                tint = Color(0xFF333333)
            )
        }

        Text(
            text = "Total Balance",
            style = MaterialTheme.typography.titleMedium,
            color = Color(0xFF666666)
        )

        IconButton(onClick = onProfileClick) {
            Icon(
                painter = painterResource(R.drawable.ic_profile),
                contentDescription = "Profile",
                tint = Color(0xFF333333)
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun PeriodSelector(
    selectedPeriod: String,
    currentDate: LocalDate,
    dateFormatter: DateTimeFormatter,
    dailyFormatter: DateTimeFormatter,
    onPeriodSelected: (String) -> Unit,
    onDateChanged: (LocalDate) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Black)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                listOf("Day", "Week", "Month", "Year").forEach { period ->
                    PeriodChip(
                        text = period,
                        isSelected = period == selectedPeriod,
                        onSelect = { onPeriodSelected(period) }
                    )
                }
            }

            DateNavigation(
                currentDate = currentDate,
                dateFormatter = dateFormatter,
                dailyFormatter = dailyFormatter,
                onDateChanged = onDateChanged
            )
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun DateNavigation(
    currentDate: LocalDate,
    dateFormatter: DateTimeFormatter,
    dailyFormatter: DateTimeFormatter,
    onDateChanged: (LocalDate) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    )
    {
        IconButton(onClick = { onDateChanged(currentDate.minusDays(1)) }) {
            Icon(
                imageVector = Icons.Default.ArrowBackIosNew,
                contentDescription = "Previous",
                tint = Color(0xFF666666)
            )
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = currentDate.format(dateFormatter),
                style = MaterialTheme.typography.titleMedium,
                color = Color(0xFF838383)
            )
            Text(
                text = currentDate.format(dailyFormatter),
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF7A7A7A)
            )
        }

        IconButton(onClick = { onDateChanged(currentDate.plusDays(1)) }) {
            Icon(
                imageVector = Icons.Default.ArrowForwardIos,
                contentDescription = "Next",
                tint = Color(0xFF666666)
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun TransactionItem(transaction: Transaction) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = transaction.category,
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                )
                Text(
                    text = transaction.date.toString(),
                    color = Color.Gray,
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Text(
                text = "${if (transaction.isExpense) "-" else "+"} %.2f DZ".format(transaction.amount),
                color = if (transaction.isExpense) Color(0xFFF44336) else Color(0xFF4CAF50),
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Composable
fun PeriodChip(text: String, isSelected: Boolean, onSelect: () -> Unit) {
    Box(
        modifier = Modifier
            .clickable(onClick = onSelect)
            .drawBehind {
                if (isSelected) {
                    val strokeWidth = 2.dp.toPx()
                    val y = size.height - strokeWidth / 2
                    drawLine(
                        color = Color(0xFFFFFFFF),
                        start = Offset(0f, y),
                        end = Offset(size.width, y),
                        strokeWidth = strokeWidth
                    )
                }
            }
            .padding(horizontal = 12.dp, vertical = 8.dp)
            .padding(bottom = if (isSelected) 4.dp else 0.dp)
    ) {
        Text(
            text = text,
            color = if (isSelected) Color.White else Color(0xFF666666),
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
            )
        )
    }
}