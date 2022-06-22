package com.example.cryptocurrencyapp.presentation.currency_listings

import com.example.cryptocurrencyapp.domain.model.CryptoCurrencyListing

data class CurrencyListingsState(
    val currenciesList: List<CryptoCurrencyListing> = emptyList(),
    val isLoading: Boolean = false,
    val query: String = "",
    val atTheEnd: Boolean = false,
    val currentPage: Int = 0,
)
