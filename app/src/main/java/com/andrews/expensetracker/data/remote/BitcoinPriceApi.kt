package com.andrews.expensetracker.data.remote

import com.andrews.expensetracker.data.remote.response.BitcoinPriceResponse
import com.andrews.expensetracker.util.AppConstants.BITCOIN_PRICE_PATH
import retrofit2.http.GET

interface BitcoinPriceApi {
    @GET(BITCOIN_PRICE_PATH)
    suspend fun getCurrentPrice(): BitcoinPriceResponse
}