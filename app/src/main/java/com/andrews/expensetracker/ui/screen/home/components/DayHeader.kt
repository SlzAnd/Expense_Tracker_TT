package com.andrews.expensetracker.ui.screen.home.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun DayHeader(
    modifier: Modifier = Modifier,
    date: LocalDate
) {
    Text(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        text = date.format(DateTimeFormatter.ofPattern("dd MMMM yyyy")),
        style = MaterialTheme.typography.titleMedium
    )
}