package com.andrews.expensetracker.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.andrews.expensetracker.data.local.model.TransactionDto

@Database(
    entities = [TransactionDto::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class TransactionDatabase : RoomDatabase() {

    abstract fun dao(): TransactionDao

    companion object {
        const val DATABASE_NAME = "transactions.db"
    }
}