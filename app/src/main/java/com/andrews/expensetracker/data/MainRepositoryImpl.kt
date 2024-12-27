package com.andrews.expensetracker.data

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.andrews.expensetracker.data.local.TransactionDao
import com.andrews.expensetracker.data.local.TransactionPagingSource
import com.andrews.expensetracker.data.mappers.toTransactionDto
import com.andrews.expensetracker.data.remote.BitcoinPriceApi
import com.andrews.expensetracker.domain.MainRepository
import com.andrews.expensetracker.domain.model.Transaction
import com.andrews.expensetracker.domain.model.TransactionsByDay
import com.andrews.expensetracker.util.AppDataStore
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest


class MainRepositoryImpl(
    private val dao: TransactionDao,
    private val bitcoinApi: BitcoinPriceApi,
    private val appDataStore: AppDataStore,
) : MainRepository {

    override fun getBalance(): Flow<Double> {
        return appDataStore.getBalance()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getTransactions(): Flow<PagingData<TransactionsByDay>> {
        return dao.getTransactionCount()
            .flatMapLatest {
                Pager(
                    config = PagingConfig(
                        pageSize = 20
                    )
                ) {
                    TransactionPagingSource(dao = dao)
                }.flow
            }
    }

    override suspend fun addTransaction(transaction: Transaction) {
        dao.insertTransaction(transaction.toTransactionDto())
        val newBalance = getBalance().first() + transaction.amount
        appDataStore.setBalance(newBalance)
    }

    override fun getBitcoinRateToUsd(): Flow<String> = appDataStore.getLastPrice().filterNotNull()

    override suspend fun checkAndUpdateBitcoinPrice() {
        val lastUpdateTimestamp = appDataStore.getTimestamp() ?: 0
        val currentTime = System.currentTimeMillis()

        if (currentTime - lastUpdateTimestamp >= UPDATE_INTERVAL) {
            try {
                val newPrice = bitcoinApi.getCurrentPrice().bpi.usd.rate
                appDataStore.saveLastPriceAndTimestamp(price = newPrice, timestamp = currentTime)
            } catch (e: Exception) {
                Log.e(TAG, "Get bitcoin info error: ${e.message}")
            }
        }
    }

    companion object {
        private val TAG = MainRepositoryImpl::class.java.simpleName
        private const val UPDATE_INTERVAL = 60 * 60 * 1000L // 1 hour
    }
}