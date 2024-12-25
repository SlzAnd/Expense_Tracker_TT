package com.andrews.expensetracker.domain.model

import java.time.LocalDateTime
import java.util.UUID

data class Transaction(
    val id: UUID = UUID.randomUUID(),
    val category: Category,
    val amount: Double,
    val dateTime: LocalDateTime = LocalDateTime.now(),
)