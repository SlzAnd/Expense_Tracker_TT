package com.andrews.expensetracker.di

import androidx.room.Room
import com.andrews.expensetracker.data.MainRepositoryImpl
import com.andrews.expensetracker.data.local.TransactionDao
import com.andrews.expensetracker.data.local.TransactionDatabase
import com.andrews.expensetracker.data.remote.BitcoinPriceApi
import com.andrews.expensetracker.domain.MainRepository
import com.andrews.expensetracker.ui.screen.home.HomeViewModel
import com.andrews.expensetracker.util.AppConstants.BITCOIN_PRICE_API_BASE_URL
import com.andrews.expensetracker.util.AppDataStore
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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

    single<BitcoinPriceApi> {
        Retrofit.Builder()
            .baseUrl(BITCOIN_PRICE_API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BitcoinPriceApi::class.java)
    }

    single<AppDataStore> {
        AppDataStore(androidContext())
    }

    single<MainRepository> {
        MainRepositoryImpl(get(), get(), get())
    }

    viewModel {
        HomeViewModel(get())
    }
}
