package com.andrews.expensetracker.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.andrews.expensetracker.data.local.model.TransactionDto
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {

    @Query("SELECT * FROM `transaction` ORDER BY date DESC LIMIT :limit OFFSET :offset")
    suspend fun loadTransactionsForPage(
        limit: Int,
        offset: Int
    ): List<TransactionDto>

    @Query("SELECT COUNT(*) FROM `transaction`")
    fun getTransactionCount(): Flow<Int>

    @Insert
    suspend fun insertTransaction(transactionDto: TransactionDto)
}