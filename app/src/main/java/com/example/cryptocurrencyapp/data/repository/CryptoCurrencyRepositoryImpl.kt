package com.example.cryptocurrencyapp.data.repository

import com.example.cryptocurrencyapp.data.mapper.toCryptoCurrencyListings
import com.example.cryptocurrencyapp.data.remote.ApiValues
import com.example.cryptocurrencyapp.data.remote.ApiValues.PAGE_SIZE
import com.example.cryptocurrencyapp.data.remote.CryptoCurrencyApi
import com.example.cryptocurrencyapp.domain.model.CryptoCurrencyListing
import com.example.cryptocurrencyapp.domain.repository.CryptoCurrencyRepository
import com.example.cryptocurrencyapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class CryptoCurrencyRepositoryImpl(
    private val api: CryptoCurrencyApi
): CryptoCurrencyRepository {
    override fun getCryptoCurrencyListings(
        page: Int
    ): Flow<Resource<List<CryptoCurrencyListing>>> {
        return flow {
            emit(Resource.Loading())

            // prvo ucita 100 valuta pa onda paginira sa 30 novih valuta

            val response = try {
                val offset = if(page * ApiValues.PAGE_SIZE == 0) 1
                    else page * PAGE_SIZE + 101 - 30

                api.getCryptoCurrencyListings(
                    offset = offset,
                    limit = if(offset == 1) 100 else PAGE_SIZE
                )
            } catch (e: IOException) {
                emit(Resource.Error("Couldn't load data"))
                null
            } catch (e: HttpException) {
                emit(Resource.Error("Couldn't load data"))
                null
            }

            response?.let { response ->
                emit(Resource.Success(
                    data = response.toCryptoCurrencyListings()
                ))
                return@flow
            }
            emit(Resource.Error(
                "Error, no data"
            ))

        }
    }
}