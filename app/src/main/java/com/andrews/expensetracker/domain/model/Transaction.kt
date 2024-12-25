package com.andrews.expensetracker.domain.model

import java.time.LocalDateTime
import java.util.UUID

data class Transaction(
    val id: UUID,
    val category: Category,
    val amount: Double,
    val dateTime: LocalDateTime,
)