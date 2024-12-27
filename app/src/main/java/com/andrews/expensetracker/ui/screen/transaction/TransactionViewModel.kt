package com.andrews.expensetracker.ui.screen.transaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andrews.expensetracker.domain.MainRepository
import com.andrews.expensetracker.domain.model.Category
import com.andrews.expensetracker.domain.model.Transaction
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TransactionViewModel(
    private val repository: MainRepository
): ViewModel() {

    private val _state = MutableStateFlow(TransactionScreenState())
    val state = _state.asStateFlow()

    private var currentBalance: Double = 0.0

    init {
        viewModelScope.launch {
            currentBalance = repository.getBalance().first()
        }
    }

    fun addTransaction(category: Category, amount: Double) {
        if (currentBalance >= amount) {
            viewModelScope.launch {
                repository.addTransaction(
                    Transaction(category = category, amount = -amount)
                )
                _state.update {
                    it.copy(isSuccess = true)
                }
            }
        } else {
            _state.update {
                it.copy(isError = true)
            }
        }
    }
}