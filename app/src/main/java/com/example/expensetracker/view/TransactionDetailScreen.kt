//package com.example.expensetracker.view
//
//import androidx.compose.runtime.Composable
//import androidx.compose.foundation.layout.*
//import androidx.compose.material3.Text
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.graphics.Color
//import androidx.compose.material3.MaterialTheme
//import androidx.navigation.NavController
//import com.example.expensetracker.view.transaction.TransactionViewModel
//import androidx.lifecycle.compose.collectAsStateWithLifecycle
//import androidx.compose.runtime.getValue
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.TopAppBar
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.ArrowBack
//import androidx.compose.material3.IconButton
//import androidx.compose.material3.Icon
//import com.example.expensetracker.model.InvoiceItem
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun TransactionDetailScreen(transactionId: String, transactionViewModel: TransactionViewModel, navController: NavController) {
//    val transaction = transactionViewModel.transactions.collectAsStateWithLifecycle()
//        .value.find { it.id == transactionId }
//
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = { Text("Transaction Details") },
//                navigationIcon = {
//                    IconButton(onClick = { navController.popBackStack() }) {
//                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
//                    }
//                }
//            )
//        }
//    ) { paddingValues ->
//        Column(
//            modifier = Modifier
//                .padding(paddingValues)
//                .padding(16.dp)
//                .fillMaxSize()
//        ) {
//            if (transaction != null) {
//                Text(
//                    text = "Transaction ID: ${transaction.id}",
//                    style = MaterialTheme.typography.bodyMedium,
//                    fontWeight = FontWeight.Bold
//                )
//                Spacer(modifier = Modifier.height(8.dp))
//
//                Text(text = "Amount: ${transaction.amount}", style = MaterialTheme.typography.bodyMedium)
//                Spacer(modifier = Modifier.height(8.dp))
//
//                Text(text = "Category: ${transaction.category}", style = MaterialTheme.typography.bodyMedium)
//                Spacer(modifier = Modifier.height(8.dp))
//
//                Text(text = "Date: ${transaction.date}", style = MaterialTheme.typography.bodyMedium)
//                Spacer(modifier = Modifier.height(16.dp))
//
//                // Display Invoice Details if Available
//                transaction.invoice?.let { invoice ->
//                    Text("Invoice Details", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
//                    Spacer(modifier = Modifier.height(8.dp))
//
//                    Text("Vendor: ${invoice.vendor.name}", style = MaterialTheme.typography.bodyMedium)
//                    Text("Vendor Address: ${invoice.vendor.address}", style = MaterialTheme.typography.bodyMedium)
//                    Spacer(modifier = Modifier.height(8.dp))
//
//                    Text("Invoice Date: ${invoice.date}", style = MaterialTheme.typography.bodyMedium)
//                    Text("Total Amount: ${invoice.total}", style = MaterialTheme.typography.bodyMedium)
//                    Text("Transaction Type: ${invoice.type}", style = MaterialTheme.typography.bodyMedium)
//                    Spacer(modifier = Modifier.height(16.dp))
//
//                    Text("Items:", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
//                    invoice.items.forEach { item ->
//                        InvoiceItem(item = item)
//                    }
//                }
//            } else {
//                Text("Transaction not found.")
//            }
//        }
//    }
//}
//
//@Composable
//fun InvoiceItem(item: InvoiceItem) {
//    Column(modifier = Modifier.padding(vertical = 4.dp)) {
//        Text("Name: ${item.name}", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Bold)
//        Text("Quantity: ${item.quantity}", style = MaterialTheme.typography.bodySmall)
//        Text("Price: ${item.price}", style = MaterialTheme.typography.bodySmall)
//        Text("Total Price: ${item.total_price}", style = MaterialTheme.typography.bodySmall)
//    }
//}
