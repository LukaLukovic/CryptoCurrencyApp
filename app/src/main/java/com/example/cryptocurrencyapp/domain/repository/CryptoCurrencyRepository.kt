package com.example.cryptocurrencyapp.domain.repository

import com.example.cryptocurrencyapp.domain.model.CryptoCurrencyListing
import com.example.cryptocurrencyapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface CryptoCurrencyRepository {

    fun getCryptoCurrencyListings(
        page: Int
    ): Flow<Resource<List<CryptoCurrencyListing>>>
}