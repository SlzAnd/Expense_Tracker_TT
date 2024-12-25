package com.andrews.expensetracker.data.mappers

import com.andrews.expensetracker.data.local.model.TransactionDto
import com.andrews.expensetracker.domain.model.Category
import com.andrews.expensetracker.domain.model.Transaction

fun TransactionDto.toTransaction(): Transaction {
    return Transaction(
        id = this.id,
        category = Category.valueOf(this.category),
        amount = this.amount,
        dateTime = this.date,
    )
}

fun Transaction.toTransactionDto(): TransactionDto {
    return TransactionDto(
        id = this.id,
        category = this.category.name,
        amount = this.amount,
        date = this.dateTime
    )
}