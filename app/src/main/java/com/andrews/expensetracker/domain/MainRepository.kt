package com.andrews.expensetracker.domain

import androidx.paging.PagingData
import com.andrews.expensetracker.domain.model.Transaction
import com.andrews.expensetracker.domain.model.TransactionsByDay
import kotlinx.coroutines.flow.Flow

interface MainRepository {
    fun getBalance(): Flow<Double>
    fun getTransactions(): Flow<PagingData<TransactionsByDay>>
    suspend fun addTransaction(transaction: Transaction)
    fun getBitcoinRateToUsd(): Flow<String>
    suspend fun checkAndUpdateBitcoinPrice()
}