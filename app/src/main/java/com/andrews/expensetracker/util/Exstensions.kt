package com.andrews.expensetracker.util

fun Double.formatBitcoin(): String {
    return if (this >= 1) {
        "%.4f".format(this).trimEnd('0').trimEnd('.')
    } else if (this >= 0.01) {
        "%.6f".format(this).trimEnd('0').trimEnd('.')
    } else {
        "%.8f".format(this).trimEnd('0').trimEnd('.')
    }
}