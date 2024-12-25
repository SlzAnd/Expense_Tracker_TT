package com.andrews.expensetracker.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime
import java.util.UUID


@Entity(tableName = "transaction")
data class TransactionDto(
    @PrimaryKey
    val id: UUID = UUID.randomUUID(),
    val category: String,
    val amount: Double,
    val date: LocalDateTime = LocalDateTime.now(),
)