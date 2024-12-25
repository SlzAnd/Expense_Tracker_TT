package com.andrews.expensetracker.data

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.andrews.expensetracker.data.local.TransactionDao
import com.andrews.expensetracker.data.local.model.TransactionDto
import com.andrews.expensetracker.data.mappers.toTransaction
import com.andrews.expensetracker.data.mappers.toTransactionDto
import com.andrews.expensetracker.data.remote.BitcoinPriceApi
import com.andrews.expensetracker.domain.MainRepository
import com.andrews.expensetracker.domain.model.Transaction
import com.andrews.expensetracker.util.AppDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map


class MainRepositoryImpl(
    private val dao: TransactionDao,
    private val bitcoinApi: BitcoinPriceApi,
    private val appDataStore: AppDataStore,
) : MainRepository {

    private val _bitcoinPrice = MutableStateFlow<String?>(null)

    override fun getBalance(): Flow<Double> {
        return appDataStore.getBalance()
    }

    override fun getTransactions(): Flow<PagingData<Transaction>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20
            )
        ) { dao.loadAllTransactions() }
            .flow
            .map { value: PagingData<TransactionDto> ->
                value.map { dto -> dto.toTransaction() }
            }
    }

    override suspend fun addTransaction(transaction: Transaction) {
        dao.insertTransaction(transaction.toTransactionDto())
        val newBalance = getBalance().first() + transaction.amount
        appDataStore.setBalance(newBalance)
    }

    override suspend fun getBitcoinRateToUsd(): Flow<String> = flow {
        val lastKnownPrice = appDataStore.getLastPrice()
        if (lastKnownPrice != null) {
            emit(lastKnownPrice)
        }

        _bitcoinPrice.filterNotNull().collect { price ->
            emit(price)
        }
    }

    override suspend fun checkAndUpdateBitcoinPrice() {
        val lastUpdateTimestamp = appDataStore.getTimestamp() ?: 0
        val currentTime = System.currentTimeMillis()

        if (currentTime - lastUpdateTimestamp >= UPDATE_INTERVAL) {
            try {
                val newPrice = bitcoinApi.getCurrentPrice().bpi.usd.rate
                appDataStore.saveLastPriceAndTimestamp(price = newPrice, timestamp = currentTime)
                _bitcoinPrice.value = newPrice
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