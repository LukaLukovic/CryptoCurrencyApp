package com.example.cryptocurrencyapp.presentation.currency_listings

sealed class CurrencyListingsEvent{
    object OnPaginateList: CurrencyListingsEvent()
}
