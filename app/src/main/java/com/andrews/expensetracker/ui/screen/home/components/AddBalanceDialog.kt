package com.andrews.expensetracker.ui.screen.home.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.andrews.expensetracker.R

@Composable
fun AddBalanceDialog(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    onConfirm: (Double) -> Unit,
) {
    var enteredValue by rememberSaveable {
        mutableStateOf("")
    }

    AlertDialog(
        modifier = modifier,
        onDismissRequest = {
            onDismiss()
        },
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text(
                    text = stringResource(R.string.cancel),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val doubleValue = enteredValue.toDoubleOrNull()
                    doubleValue?.let {
                        onConfirm(it)
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary,
                    contentColor = MaterialTheme.colorScheme.primary
                ),
            ) {
                Text(
                    text = stringResource(R.string.add),
                    fontWeight = FontWeight.Bold,
                )
            }
        },
        title = {
            Text(
                text = stringResource(R.string.add_balance),
                fontSize = 17.sp,
                color = MaterialTheme.colorScheme.surfaceTint,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        },
        text = {
            OutlinedTextField(
                value = enteredValue,
                onValueChange = { newText ->
                    enteredValue = newText
                },
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
                    focusedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
                    unfocusedBorderColor = Color.Transparent,
                    focusedBorderColor = Color.Transparent,
                    cursorColor = MaterialTheme.colorScheme.surfaceTint,
                    focusedTextColor = MaterialTheme.colorScheme.surfaceTint,
                    unfocusedTextColor = MaterialTheme.colorScheme.surfaceTint
                ),
                placeholder = {
                    Text(
                        text = stringResource(R.string.amount),
                        color = MaterialTheme.colorScheme.surfaceTint.copy(alpha = 0.5f)
                    )
                },
                shape = RoundedCornerShape(12.dp),
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Decimal
                ),
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    )
}