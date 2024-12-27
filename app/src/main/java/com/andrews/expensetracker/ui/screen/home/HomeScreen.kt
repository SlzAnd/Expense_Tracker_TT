package com.andrews.expensetracker.ui.screen.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.andrews.expensetracker.R
import com.andrews.expensetracker.ui.navigation.Screen
import com.andrews.expensetracker.ui.screen.home.components.AddBalanceDialog
import com.andrews.expensetracker.ui.screen.home.components.BtcRateItem
import com.andrews.expensetracker.ui.screen.home.components.DayHeader
import com.andrews.expensetracker.ui.screen.home.components.TransactionItem
import com.andrews.expensetracker.util.formatBitcoin
import org.koin.androidx.compose.koinViewModel


@Composable
fun HomeScreen(
    navController: NavHostController
) {
    val viewModel = koinViewModel<HomeViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val transactions = viewModel.transactions.collectAsLazyPagingItems()

    var showBalanceDialog by rememberSaveable {
        mutableStateOf(false)
    }

    LifecycleEventEffect(Lifecycle.Event.ON_START) {
        viewModel.checkBtcRate()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .statusBarsPadding()
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            BtcRateItem(
                modifier = Modifier
                    .align(Alignment.End),
                rate = state.btcRate
            )
            Text(
                text = stringResource(R.string.balance),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
            Row(
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.bitcoin),
                    contentDescription = "bitcoin",
                    modifier = Modifier
                        .size(34.dp)
                )
                Text(
                    text = state.balance.formatBitcoin(),
                    fontSize = 48.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )

                IconButton(
                    modifier = Modifier,
                    onClick = {
                        showBalanceDialog = true
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.AddCircle,
                        contentDescription = "Add",
                        modifier = Modifier
                            .size(34.dp),
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            Button(
                onClick = { navController.navigate(Screen.TransactionScreen) },
            ) {
                Text(
                    text = stringResource(R.string.new_transaction),
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
                Icon(
                    Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = null,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }

            Spacer(
                Modifier
                    .padding(vertical = 16.dp)
                    .fillMaxWidth()
                    .height(2.dp)
                    .background(Color.LightGray)
            )

            if (transactions.itemCount > 0) {
                LazyColumn {
                    items(
                        count = transactions.itemCount,
                        key = transactions.itemKey { it.date },
                        contentType = transactions.itemContentType { "Transactions" }
                    ) {index ->
                        transactions[index]?.let {transactionsByDay ->
                            DayHeader(date = transactionsByDay.date)
                            transactionsByDay.transactions.forEach {
                                TransactionItem(
                                    transaction = it
                                )
                                Spacer(Modifier.height(8.dp))
                            }
                        }
                    }
                }
            } else {
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.no_content),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.size(120.dp),
                        )
                        Text(
                            text = stringResource(R.string.no_transactions),
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        }
        if (showBalanceDialog) {
            AddBalanceDialog(
                onDismiss = {
                    showBalanceDialog = false
                },
                onConfirm = { enteredBalance ->
                    viewModel.updateBalance(enteredBalance)
                    showBalanceDialog = false
                }
            )
        }
    }
}