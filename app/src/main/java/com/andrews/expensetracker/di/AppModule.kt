package com.andrews.expensetracker.di

import androidx.room.Room
import com.andrews.expensetracker.data.local.TransactionDao
import com.andrews.expensetracker.data.local.TransactionDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {

    single<TransactionDatabase> {
        Room.databaseBuilder(
            this.androidContext(),
            TransactionDatabase::class.java,
            TransactionDatabase.DATABASE_NAME
        )
            .build()
    }

    single<TransactionDao> {
        get<TransactionDatabase>().dao()
    }


}
