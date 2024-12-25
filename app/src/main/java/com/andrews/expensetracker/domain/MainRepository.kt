package com.andrews.expensetracker.domain

import androidx.paging.PagingData
import com.andrews.expensetracker.domain.model.Transaction
import kotlinx.coroutines.flow.Flow

interface MainRepository {
    fun getBalance(): Flow<Double>
    fun getTransactions(): Flow<PagingData<Transaction>>
    suspend fun addTransaction(transaction: Transaction)
    suspend fun getBitcoinRateToUsd(): Flow<String>
    suspend fun checkAndUpdateBitcoinPrice()
}