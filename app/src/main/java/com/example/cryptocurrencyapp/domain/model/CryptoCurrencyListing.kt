package com.example.cryptocurrencyapp.domain.model

data class CryptoCurrencyListing(
    val id: Int,
    val name: String,
    val symbol: String,
    val price: String,
    val ranking: String
)
