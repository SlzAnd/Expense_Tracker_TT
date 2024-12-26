package com.andrews.expensetracker.ui.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.andrews.expensetracker.ui.screen.home.HomeScreen
import com.andrews.expensetracker.ui.screen.transaction.TransactionScreen


@Composable
fun NavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home
    ) {
        composable<Screen.Home> {
            HomeScreen(navController = navController)
        }

        composable<Screen.TransactionScreen>(
            enterTransition = {
                slideInHorizontally(
                    animationSpec = tween(500),
                    initialOffsetX = { fullWidth -> fullWidth }
                )
            },
            exitTransition = {
                slideOutHorizontally(
                    animationSpec = tween(500),
                    targetOffsetX = { fullWidth -> fullWidth }
                )
            }
        ) {
            TransactionScreen(navController = navController)
        }
    }
}