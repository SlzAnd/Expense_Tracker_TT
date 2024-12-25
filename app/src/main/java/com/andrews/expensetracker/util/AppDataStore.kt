package com.andrews.expensetracker.util

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class AppDataStore(private val context: Context) {

    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
            name = "AppDataStore"
        )
        val BALANCE = doublePreferencesKey("balance")
        val LAST_UPDATE_TIMESTAMP = longPreferencesKey("last_update_timestamp")
        val LAST_BITCOIN_PRICE = stringPreferencesKey("last_bitcoin_price")
    }

    fun getBalance(): Flow<Double> {
        return context.dataStore.data.map {pref ->
            pref[BALANCE] ?: 0.0
        }
    }

    suspend fun setBalance(newBalance: Double) {
        context.dataStore.edit { preferences ->
            preferences[BALANCE] = newBalance
        }
    }

    suspend fun getLastPrice(): String? {
        return context.dataStore.data.first()[LAST_BITCOIN_PRICE]
    }

    suspend fun getTimestamp(): Long? {
        return context.dataStore.data.first()[LAST_UPDATE_TIMESTAMP]
    }

    suspend fun saveLastPriceAndTimestamp(price: String, timestamp: Long) {
        context.dataStore.edit { preferences ->
            preferences[LAST_BITCOIN_PRICE] = price
            preferences[LAST_UPDATE_TIMESTAMP] = timestamp
        }
    }
}