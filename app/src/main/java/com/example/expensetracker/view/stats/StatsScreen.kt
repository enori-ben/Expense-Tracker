package com.example.expensetracker.view.stats


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.expensetracker.view.transaction.TransactionViewModel

@Composable
fun StatsScreen(
    navController: NavController,
    viewModel: TransactionViewModel
) {
    val totalIncome by viewModel.totalIncome.collectAsStateWithLifecycle()
    val totalExpense by viewModel.totalExpense.collectAsStateWithLifecycle()
    val totalBalance by viewModel.totalBalance.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ActualStatsScreen(
            navController = navController,
            totalIncome = totalIncome,
            totalExpense = totalExpense,
            totalBalance = totalBalance
        )
    }
}

@Composable
fun ActualStatsScreen(
    navController: NavController,
    totalIncome: Double,
    totalExpense: Double,
    totalBalance: Double,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F6FA)) // App background color
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header
        Text(
            text = "Statistics",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF333333), // App primary text color
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Income Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFC8E6C9)), // Light green
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Total Income",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )
                Text(
                    text = "%.2f DZ".format(totalIncome),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF4CAF50)
                )
            }
        }

        // Expense Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFFFCDD2)), // Light red
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Total Expenses",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )
                Text(
                    text = "%.2f DZ".format(totalExpense),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFF44336)
                )
            }
        }

        // Balance Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFBBDEFB)), // Light blue
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Remaining Balance",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )
                Text(
                    text = "%.2f DZ".format(totalBalance),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1976D2)
                )
            }
        }

        // Circle instead of Graph Placeholder
        Box(
            modifier = Modifier
                .size(200.dp) // حجم الدائرة
                .padding(vertical = 16.dp)
                .clip(CircleShape) // قص إلى دائرة
                .background(Color(0xFFE0E0E0)), // لون خلفية الدائرة
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Graph Here",
                color = Color.Gray,
                fontSize = 16.sp
            )
        }
    }
}
