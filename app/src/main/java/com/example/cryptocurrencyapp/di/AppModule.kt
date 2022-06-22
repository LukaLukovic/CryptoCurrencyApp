package com.example.cryptocurrencyapp.di

import com.example.cryptocurrencyapp.data.remote.ApiValues
import com.example.cryptocurrencyapp.data.remote.CryptoCurrencyApi
import com.example.cryptocurrencyapp.data.remote.dto.CryptoCurrencyResponse
import com.example.cryptocurrencyapp.data.repository.CryptoCurrencyRepositoryImpl
import com.example.cryptocurrencyapp.domain.repository.CryptoCurrencyRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesCryptoCurrencyApi(): CryptoCurrencyApi {
        return Retrofit.Builder()
            .baseUrl(ApiValues.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create()
    }
    @Provides
    @Singleton
    fun providesRepository(api: CryptoCurrencyApi): CryptoCurrencyRepository {
        return CryptoCurrencyRepositoryImpl(api)
    }
}