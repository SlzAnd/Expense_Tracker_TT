package com.andrews.expensetracker.data.remote.response

import com.google.gson.annotations.SerializedName

data class BitcoinPriceResponse(
    val bpi: Bpi,
    val chartName: String,
    val disclaimer: String,
    val time: Time
)

data class Bpi(
    @SerializedName("EUR")
    val eur: EUR,
    @SerializedName("GBP")
    val gbp: GBP,
    @SerializedName("USD")
    val usd: USD
)

data class EUR(
    val code: String,
    val description: String,
    val rate: String,
    @SerializedName("rate_float")
    val rateFloat: Double,
    val symbol: String
)

data class GBP(
    val code: String,
    val description: String,
    val rate: String,
    @SerializedName("rate_float")
    val rateFloat: Double,
    val symbol: String
)

data class Time(
    val updated: String,
    val updatedISO: String,
    @SerializedName("updateduk")
    val updatedUK: String
)

data class USD(
    val code: String,
    val description: String,
    val rate: String,
    @SerializedName("rate_float")
    val rateFloat: Double,
    val symbol: String
)