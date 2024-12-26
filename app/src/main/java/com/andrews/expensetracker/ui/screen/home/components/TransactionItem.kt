package com.andrews.expensetracker.ui.screen.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.andrews.expensetracker.R
import com.andrews.expensetracker.domain.model.Transaction
import com.andrews.expensetracker.ui.theme.LightGreen
import com.andrews.expensetracker.ui.theme.LightRed
import java.time.format.DateTimeFormatter

@Composable
fun TransactionItem(
    modifier: Modifier = Modifier,
    transaction: Transaction
) {
    val color = if(transaction.amount > 0) LightGreen else LightRed

    ListItem(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp)),
        headlineContent = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Image(
                    painter = painterResource(R.drawable.bitcoin),
                    contentDescription = "bitcoin",
                    modifier = Modifier
                        .size(24.dp)
                )

                Spacer(Modifier.width(8.dp))

                Text(
                    text = transaction.amount.toString(),
                    style = MaterialTheme.typography.titleLarge
                )
            }
        },
        leadingContent = {
            Text(
                text = transaction.dateTime.format(DateTimeFormatter.ofPattern("HH:mm")),
                style = MaterialTheme.typography.titleSmall
            )
        },
        trailingContent = {
            if (transaction.amount < 0) {
                Text(
                    text = transaction.category.name,
                    style = MaterialTheme.typography.titleSmall
                )
            }
        },
        colors = ListItemDefaults.colors(
            containerColor = color
        )
    )
}