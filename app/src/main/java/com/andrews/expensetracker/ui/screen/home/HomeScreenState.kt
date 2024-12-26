package com.andrews.expensetracker.ui.screen.home

import androidx.paging.PagingData
import com.andrews.expensetracker.domain.model.TransactionsByDay

data class HomeScreenState(
    val balance: Double = 0.0,
    val btcRate: String = "",
    val transactions: PagingData<TransactionsByDay> = PagingData.empty(),
)
