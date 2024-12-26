package com.andrews.expensetracker.domain.model

import java.time.LocalDate

data class TransactionsByDay(
    val date: LocalDate,
    val transactions: List<Transaction>
)
