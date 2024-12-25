package com.andrews.expensetracker

import android.app.Application
import com.andrews.expensetracker.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ExpenseTrackerApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@ExpenseTrackerApplication)
            modules(appModule)
        }
    }
}