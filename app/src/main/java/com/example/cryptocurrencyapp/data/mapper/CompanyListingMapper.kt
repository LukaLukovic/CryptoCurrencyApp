package com.example.cryptocurrencyapp.data.mapper

import com.example.cryptocurrencyapp.data.remote.dto.CryptoCurrencyResponse
import com.example.cryptocurrencyapp.domain.model.CryptoCurrencyListing
import kotlin.math.pow
import kotlin.math.roundToInt


fun CryptoCurrencyResponse.toCryptoCurrencyListings(): List<CryptoCurrencyListing> {



    return data.map { listing ->
        val price = listing.quote?.USD?.price?.toFloat()
        val formattedPrice = price?.roundDecimal().toString()
        CryptoCurrencyListing(
            id = listing.id,
            name = listing.name,
            symbol = listing.symbol,
            price = formattedPrice,
            ranking = listing.cmc_rank.toString()
        )
    }

}

private fun Float.roundDecimal(rounding: Int = 2): Float {
    val rounding = 10f.pow(rounding)
    return ( this * rounding ).roundToInt() / rounding
}

