package com.andrews.expensetracker.ui.navigation

import kotlinx.serialization.Serializable

sealed interface Screen {
    @Serializable
    data object Home: Screen
    @Serializable
    data object TransactionScreen: Screen
}