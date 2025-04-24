
package com.example.expensetracker.model

import kotlinx.serialization.Serializable


@Serializable
data class Vendor(
    val name: String,
    val address: String
)

@Serializable
data class InvoiceResult(
    val date: String,
    val total: Double,
    val type: String,
    val items: List<InvoiceItem>,
    val vendor: Vendor
)
@Serializable
data class InvoiceItem(
    val name: String,
    val quantity: Int,
    val price: Double,
    val total_price: Double
)
