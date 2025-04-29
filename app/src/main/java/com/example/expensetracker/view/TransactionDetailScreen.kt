@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.expensetracker.view

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.MaterialTheme
import androidx.navigation.NavController
import com.example.expensetracker.view.transaction.TransactionViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.runtime.getValue
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.Icon
import androidx.compose.ui.text.style.TextAlign
import com.example.expensetracker.model.InvoiceItem
import java.time.format.DateTimeFormatter
import androidx.compose.runtime.Composable
import com.example.expensetracker.view.transaction.Transaction
import com.example.expensetracker.model.InvoiceResult

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionDetailScreen(
    transactionId: String,
    viewModel: TransactionViewModel,
    navController: NavController
) {
    val transactions by viewModel.transactions.collectAsStateWithLifecycle()
    val transaction = transactions.find { it.id == transactionId }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Transaction Details") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBackIosNew, contentDescription = "Back")
                    }

                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            if (transaction != null) {
                if (transaction.invoiceDetails == null) {
                    // Display manual transaction details
                    ManualTransactionDetails(transaction = transaction)
                } else {
                    // Display invoice transaction details
                    InvoiceTransactionDetails(transaction = transaction)
                }
            } else {
                Text(
                    text = "Transaction not found",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun ManualTransactionDetails(transaction: Transaction) {
    // Basic Information Section
    DetailItem("Amount", "%,.2f DZD".format(transaction.amount))
    DetailItem("Category", transaction.category)
    DetailItem(
        "Type",
        if (transaction.isExpense) "Expense" else "Income",
        color = if (transaction.isExpense) Color.Red else Color.Green
    )
    DetailItem("Date", transaction.date.format(DateTimeFormatter.ISO_LOCAL_DATE))

    Spacer(modifier = Modifier.height(16.dp))

    // Manual Description Section
    transaction.manualDescription.takeIf { it.isNotEmpty() }?.let {
        SectionTitle("Description")
        Text(
            text = it,
            modifier = Modifier.padding(vertical = 8.dp),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
private fun InvoiceTransactionDetails(transaction: Transaction) {
    transaction.invoiceDetails?.let { invoice ->
        SectionTitle("Invoice Details")

        // Vendor Information
        DetailItem("Vendor", invoice.vendor.name)
        DetailItem("Address", invoice.vendor.address)

        // Invoice Metadata
        DetailItem("Invoice Date", invoice.date)
        DetailItem("Total Amount", "%,.2f DZD".format(invoice.total))
        DetailItem("Category", invoice.type)
        DetailItem(
            "Type",
            if (transaction.isExpense) "Expense" else "Income",
            color = if (transaction.isExpense) Color.Red else Color.Green
        )
        // Items List
        SectionTitle("Purchased Items")
        invoice.items.forEach { item ->
            InvoiceItemRow(item)
        }
    }
}

@Composable
private fun SectionTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium.copy(
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        ),
        modifier = Modifier.padding(vertical = 8.dp)
    )
}

@Composable
private fun DetailItem(label: String, value: String, color: Color = MaterialTheme.colorScheme.onBackground) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = color
        )
    }
}

@Composable
private fun InvoiceItemRow(item: InvoiceItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = "Total Price:  %,.2f DZD".format(item.totalPrice),
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Unit Price: %,.2f".format(item.price))
                Text("Quantity: ${item.quantity}")

            }
        }
    }
}
