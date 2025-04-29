package com.example.expensetracker.view.mainscreen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDatePickerState
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.expensetracker.R
import com.example.expensetracker.navigation.NavItem
import com.example.expensetracker.repository.Routes
import com.example.expensetracker.view.DrawerScreen
import com.example.expensetracker.view.scan.Scanning
import com.example.expensetracker.view.stats.StatsScreen
import com.example.expensetracker.view.transaction.Transaction
import com.example.expensetracker.view.transaction.TransactionViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlinx.coroutines.CoroutineScope
import java.util.UUID
import java.time.ZoneId
import java.time.Instant

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreen(navController: NavController, transactionViewModel: TransactionViewModel) {

    val totalBalance by transactionViewModel.totalBalance.collectAsStateWithLifecycle()

    val viewModel: MainViewModel = viewModel()

    val navItems = listOf(
        NavItem("Home", painterResource(R.drawable.home)),
        NavItem("Scan", painterResource(R.drawable.scanning)),
        NavItem("Stats", painterResource(R.drawable.st))
    )
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerScreen(
                onClose = { scope.launch { drawerState.close() } },
                navController = navController,
                totalBalance = totalBalance,
                drawerState = drawerState, // إضافة هذه المعلمة
                scope = scope // إضاإضافة هذه المعلمة
            )
        }
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = Color(0xFFF5F5F5),
            bottomBar = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .drawBehind {
                            drawLine(
                                color = Color.Gray.copy(alpha = 0.6f),
                                start = Offset.Zero,
                                end = Offset(size.width, 0f),
                                strokeWidth = 10f
                            )
                        }
                ) {
                    NavigationBar(containerColor = Color.Black) {
                        navItems.forEachIndexed { index, item ->
                            NavigationBarItem(
                                selected = viewModel.selectedIndex == index,
                                onClick = { viewModel.updateIndex(index) },
                                icon = {
                                    Icon(
                                        painter = item.icon,
                                        contentDescription = item.label,
                                        modifier = Modifier.size(38.dp),
                                        tint = if (viewModel.selectedIndex == index)
                                            Color(0xFFFFBB0C) else Color.Gray
                                    )
                                }

                            )
                        }
                    }
                }
            }
        ) { innerPadding ->
            when (viewModel.selectedIndex) {
                0 -> HomeScreenContent(
                    modifier = Modifier.padding(innerPadding),
                    navController = navController,
                    viewModel = transactionViewModel,
                    scope = scope,
                    drawerState = drawerState
                )

                1 -> Scanning(
                    onClose = { navController.navigate(Routes.MAIN_SCREEN) },
                    onConfirm = { navController.navigate(Routes.MAIN_SCREEN) },
                    viewModel = transactionViewModel
                )

                2 -> StatsScreen(navController, transactionViewModel)
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun HomeScreenContent(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: TransactionViewModel,
    scope: CoroutineScope,
    drawerState: DrawerState
) {

    val transactions by viewModel.transactions.collectAsStateWithLifecycle()
    val totalBalance by viewModel.totalBalance.collectAsStateWithLifecycle()
    val totalIncome by viewModel.totalIncome.collectAsStateWithLifecycle()
    val totalExpense by viewModel.totalExpense.collectAsStateWithLifecycle()

    var selectedPeriod by remember { mutableStateOf("Day") }
    var currentDate by remember { mutableStateOf(LocalDate.now()) }

    val dateFormatter = remember { DateTimeFormatter.ofPattern("dd MMM yyyy") }
    val dailyFormatter = remember { DateTimeFormatter.ofPattern("EEEE, d MMM") }

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
            .background(Color(0xD7020509))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            HeaderSection(
                scope = scope,
                drawerState = drawerState,
                navController = navController
            )
            BalanceSummarySection(totalBalance, totalIncome, totalExpense)

            PeriodSelector(
                selectedPeriod = selectedPeriod,
                currentDate = currentDate,
                dateFormatter = dateFormatter,
                dailyFormatter = dailyFormatter,
                onPeriodSelected = { selectedPeriod = it },
                onDateChanged = { currentDate = it }
            )

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
                                color = Color.Gray
                            )
                        )
                    }
                } else {
                    items(filteredTransactions.reversed()) { transaction ->
                        TransactionItem(transaction = transaction) {
                            navController.navigate("${Routes.TRANSACTION_DETAIL}/${transaction.id}")
                        }
                    }
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 600.dp)
        ) {
            // Floating Action Button
            FAB(navController)
        }
    }
}

@Composable
private fun HeaderSection(
    scope: CoroutineScope,
    drawerState: DrawerState,
    navController: NavController
) {
    Spacer(modifier = Modifier.height(16.dp))

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(23.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { scope.launch { drawerState.open() } }) {
            Icon(
                painter = painterResource(R.drawable.menu),
                contentDescription = "Menu",
                tint = Color.Black
            )
        }

        IconButton(onClick = { navController.navigate(Routes.PROFILE_SCREEN) }) {
            Icon(
                painter = painterResource(R.drawable.ic_profile),
                contentDescription = "Profile",
                tint = Color.Black
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


@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun DateNavigation(
    currentDate: LocalDate,
    dateFormatter: DateTimeFormatter,
    dailyFormatter: DateTimeFormatter,
    onDateChanged: (LocalDate) -> Unit
) {
    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = currentDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
    )

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

        Column(horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.clickable { showDatePicker = true } //Make the date clickable
        ) {
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

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    showDatePicker = false
                    datePickerState.selectedDateMillis?.let { millis ->
                        val instant = Instant.ofEpochMilli(millis)
                        val newDate = instant.atZone(ZoneId.systemDefault()).toLocalDate()
                        onDateChanged(newDate)
                    }
                }) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("Cancel")
                }
            }
        ) {
            DatePicker(
                state = datePickerState,
                title = { Text(text = "Select Day",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(16.dp)) }
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun TransactionItem(transaction: Transaction, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
            .clickable { onClick() },  //Make clickable
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
                text = "${if (transaction.isExpense) "-" else "+"} %.2f DZD".format(transaction.amount),
                color = if (transaction.isExpense) Color(0xFFF44336) else Color(0xFF4CAF50),
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.bodyLarge
            )
        }

//            if (transaction.invoiceDetails.isNotEmpty()) {
//                Spacer(modifier = Modifier.height(8.dp))
//                Text(
//                    text = transaction.invoiceDetails,
//                    style = MaterialTheme.typography.bodySmall,
//                    color = Color.Gray
//                )
//            }
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

@Composable
fun FAB(navController: NavController) {

    FloatingActionButton(
        onClick = {
            navController.navigate(Routes.ADD_TRANSACTION)
        },
        modifier = Modifier
            .padding(24.dp)
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
    Column(modifier = Modifier.padding(horizontal = 24.dp)) {
        Text(
            text = "Total Balance",
            style = MaterialTheme.typography.titleLarge,
            color = Color.White
        )
        Text(
            text = "%,.2f DZD".format(totalBalance),
            style = MaterialTheme.typography.displaySmall.copy(
                fontSize = 33.sp,
                fontWeight = FontWeight.Bold,
                color = if (totalBalance >= 0) Color(0xFF4CAF50) else Color(0xFFF44336)
            )
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp),
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
        Text(
            text = label,
            style = MaterialTheme.typography.titleLarge,
            color = Color.White // ← أضف هذا السطر لتحديد اللون الأبيض
        )
        Text(
            text = "%,.2f DZD".format(amount),
            color = color,
            style = MaterialTheme.typography.titleMedium
        )
    }
}