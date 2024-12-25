package com.andrews.expensetracker.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.andrews.expensetracker.data.local.model.TransactionDto

@Dao
interface TransactionDao {

    @Query("SELECT * FROM `transaction`")
    fun loadAllTransactions(): PagingSource<Int, TransactionDto>

    @Insert
    suspend fun insertTransaction(transactionDto: TransactionDto)
}