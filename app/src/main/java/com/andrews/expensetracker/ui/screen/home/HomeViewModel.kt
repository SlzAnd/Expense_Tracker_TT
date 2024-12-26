package com.andrews.expensetracker.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.andrews.expensetracker.domain.MainRepository
import com.andrews.expensetracker.domain.model.Category
import com.andrews.expensetracker.domain.model.Transaction
import com.andrews.expensetracker.domain.model.TransactionsByDay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: MainRepository
) : ViewModel() {

    private val _state = MutableStateFlow(HomeScreenState())
    val state = combine(
        _state,
        repository.getBalance(),
        repository.getBitcoinRateToUsd()
    ) { state, newBalance, newBtcRate ->
        println("Inside combine: newRate: $newBtcRate, newBalance: $newBalance")
        state.copy(
            balance = newBalance,
            btcRate = newBtcRate
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), HomeScreenState())

    val transactions: Flow<PagingData<TransactionsByDay>> =
        repository.getTransactions().cachedIn(viewModelScope)

    fun updateBalance(value: Double) {
        viewModelScope.launch {
            repository.addTransaction(
                Transaction(category = Category.OTHER, amount = value)
            )
        }
    }

    fun addTransaction(category: Category, amount: Double) {
        viewModelScope.launch {
            repository.addTransaction(
                Transaction(category = category, amount = amount)
            )
        }
    }

    fun checkBtcRate() {
        viewModelScope.launch {
            repository.checkAndUpdateBitcoinPrice()
        }
    }
}