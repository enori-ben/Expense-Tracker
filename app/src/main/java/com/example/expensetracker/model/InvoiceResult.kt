@file:Suppress("PLUGIN_IS_NOT_ENABLED")

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
    val quantity: Double, // Changed type to Double
    val price: Double,
    val totalPrice: Double
)