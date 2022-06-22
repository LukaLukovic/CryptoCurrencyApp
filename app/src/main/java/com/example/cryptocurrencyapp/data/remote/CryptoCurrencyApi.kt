package com.example.cryptocurrencyapp.data.remote

import com.example.cryptocurrencyapp.data.remote.dto.CryptoCurrencyResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CryptoCurrencyApi {


    @GET("cryptocurrency/listings/latest")
    suspend fun getCryptoCurrencyListings(
        @Query("CMC_PRO_API_KEY") apiKey: String = ApiValues.API_KEY,
        @Query("start") offset: Int,
        @Query("limit") limit: Int,
        @Query("sort") sort: String = "market_cap"
    ): CryptoCurrencyResponse
}