package com.example.cryptocurrencyapp.data.remote.dto

data class CryptoCurrencyResponse(
    val `data`: List<Data>,
    val status: Status
)