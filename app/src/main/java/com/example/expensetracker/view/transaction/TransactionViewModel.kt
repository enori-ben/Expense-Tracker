package com.example.expensetracker.view.transaction

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDate
import java.util.UUID

data class Transaction(
    val id: String = UUID.randomUUID().toString(),
    val amount: Double,
    val isExpense: Boolean,
    val category: String,
    val date: LocalDate
)

class TransactionViewModel(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _transactions = MutableStateFlow(
        savedStateHandle.get<List<Transaction>>("transactions") ?: emptyList()
    )
    val transactions: StateFlow<List<Transaction>> = _transactions

    private val _totalBalance = MutableStateFlow(
        savedStateHandle.get<Double>("balance") ?: 0.0
    )
    val totalBalance: StateFlow<Double> = _totalBalance

    private val _totalIncome = MutableStateFlow(
        savedStateHandle.get<Double>("income") ?: 0.0
    )
    val totalIncome: StateFlow<Double> = _totalIncome

    private val _totalExpense = MutableStateFlow(
        savedStateHandle.get<Double>("expense") ?: 0.0
    )
    val totalExpense: StateFlow<Double> = _totalExpense

    fun addTransaction(
        amount: Double,
        isExpense: Boolean,
        category: String,
        date: LocalDate
    ) {
        if (amount <= 0) {
            println("TransactionViewModel - Error: Amount must be positive")
            return
        }

        val transaction = Transaction(
            amount = amount,
            isExpense = isExpense,
            category = if (isExpense) category else "Income",
            date = date
        )

        println("TransactionViewModel - Adding transaction: $transaction")

        _transactions.update { it + transaction }

        if (isExpense) {
            _totalExpense.update { it + amount }
            _totalBalance.update { it - amount }
        } else {
            _totalIncome.update { it + amount }
            _totalBalance.update { it + amount }
        }

        saveAllStates()
        debugPrint()
    }

    fun deleteTransaction(transactionId: String) {
        val transaction = _transactions.value.find { it.id == transactionId }
        transaction?.let {
            _transactions.update { list -> list.filterNot { t -> t.id == transactionId } }

            if (it.isExpense) {
                _totalExpense.update { current -> current - it.amount }
                _totalBalance.update { current -> current + it.amount }
            } else {
                _totalIncome.update { current -> current - it.amount }
                _totalBalance.update { current -> current - it.amount }
            }

            saveAllStates()
        }
    }

    private fun saveAllStates() {
        savedStateHandle["transactions"] = _transactions.value
        savedStateHandle["balance"] = _totalBalance.value
        savedStateHandle["income"] = _totalIncome.value
        savedStateHandle["expense"] = _totalExpense.value
        println("TransactionViewModel - States saved to SavedStateHandle")
    }

    private fun debugPrint() {
        println("------------------ ViewModel State -----------------------")
        println("TransactionViewModel - Transactions count: ${_transactions.value.size}")
        _transactions.value.forEach { println("TransactionViewModel -   Transaction: $it") }
        println("TransactionViewModel - Current balance: ${_totalBalance.value}")
        println("TransactionViewModel - Total income: ${_totalIncome.value}")
        println("TransactionViewModel - Total expense: ${_totalExpense.value}")
        println("-----------------------------------------------------------")
    }
}