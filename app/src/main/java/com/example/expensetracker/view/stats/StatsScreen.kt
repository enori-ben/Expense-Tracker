package com.example.expensetracker.view.stats

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.expensetracker.R
import com.example.expensetracker.view.transaction.TransactionViewModel
import androidx.compose.ui.graphics.Color


/**
 * Stats Screen displays financial overview including income, expenses, and balance.
 *
 * @param navController Navigation controller for handling navigation
 * @param viewModel ViewModel that provides financial data
 */
@Composable
fun StatsScreen(
    navController: NavController,
    viewModel: TransactionViewModel
) {
    // Collect financial data from ViewModel
    val totalIncome by viewModel.totalIncome.collectAsStateWithLifecycle()
    val totalExpense by viewModel.totalExpense.collectAsStateWithLifecycle()
    val totalBalance by viewModel.totalBalance.collectAsStateWithLifecycle()

    // Main content layout
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        StatsContent(
            totalIncome = totalIncome,
            totalExpense = totalExpense,
            totalBalance = totalBalance
        )
    }
}

/**
 * Main content layout for Stats Screen
 *
 * @param totalIncome Total income value
 * @param totalExpense Total expenses value
 * @param totalBalance Current balance value
 */
@Composable
private fun StatsContent(
    totalIncome: Double,
    totalExpense: Double,
    totalBalance: Double
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Screen title
        Text(
            text = stringResource(R.string.stats_title),
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary
        )

        // Financial cards
        FinancialCardsSection(
            totalIncome = totalIncome,
            totalExpense = totalExpense,
            totalBalance = totalBalance
        )

        // Data visualization placeholder
        DataVisualizationPlaceholder()
    }
}

/**
 * Section containing financial summary cards
 *
 * @param totalIncome Total income value
 * @param totalExpense Total expenses value
 * @param totalBalance Current balance value
 */
@Composable
private fun FinancialCardsSection(
    totalIncome: Double,
    totalExpense: Double,
    totalBalance: Double
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Income card
        FinancialCard(
            titleRes = R.string.income_title,
            amount = totalIncome,
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            textColor = MaterialTheme.colorScheme.onPrimaryContainer
        )

        // Expense card
        FinancialCard(
            titleRes = R.string.expense_title,
            amount = totalExpense,
            containerColor = MaterialTheme.colorScheme.errorContainer,
            textColor = MaterialTheme.colorScheme.onErrorContainer
        )

        // Balance card
        FinancialCard(
            titleRes = R.string.balance_title,
            amount = totalBalance,
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            textColor = MaterialTheme.colorScheme.onSecondaryContainer
        )
    }
}

/**
 * Reusable financial card component
 *
 * @param titleRes String resource ID for card title
 * @param amount Financial amount to display
 * @param containerColor Background color of the card
 * @param textColor Text color for the card content
 */
@Composable
private fun FinancialCard(
    titleRes: Int,
    amount: Double,
    containerColor: Color,
    textColor: Color
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = containerColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Card title
            Text(
                text = stringResource(titleRes),
                style = MaterialTheme.typography.titleLarge,
                color = textColor
            )

            // Formatted amount
            Text(
                text = formatCurrency(amount),
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.ExtraBold,
                color = textColor
            )
        }
    }
}

/**
 * Placeholder for data visualization
 */
@Composable
private fun DataVisualizationPlaceholder() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.surfaceVariant),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(R.string.analytics_placeholder),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

/**
 * Formats currency value with locale-appropriate formatting
 *
 * @param amount Currency amount to format
 * @return Formatted currency string
 */
@Composable
private fun formatCurrency(amount: Double): String {
    return "%,.2f ${stringResource(R.string.currency)}".format(amount)
}