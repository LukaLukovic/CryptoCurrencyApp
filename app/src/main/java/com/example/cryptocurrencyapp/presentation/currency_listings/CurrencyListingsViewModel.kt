package com.example.cryptocurrencyapp.presentation.currency_listings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptocurrencyapp.domain.repository.CryptoCurrencyRepository
import com.example.cryptocurrencyapp.util.Resource
import com.example.cryptocurrencyapp.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrencyListingsViewModel @Inject constructor(
    private val repository: CryptoCurrencyRepository,
) : ViewModel() {


    var state by mutableStateOf(CurrencyListingsState())
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private var paginateJob: Job? = null

    init {
        getListings(state.currentPage)
    }


    fun onEvent(event: CurrencyListingsEvent) {
        when (event) {
            is CurrencyListingsEvent.OnPaginateList -> {
                getListings(state.currentPage)
            }
        }
    }

    private fun getListings(
        page: Int,
    ) {
        paginateJob?.cancel()
        paginateJob = viewModelScope.launch {
            delay(1500L) //ovo je samo da bi progress bar bio vidljiv
            repository
                .getCryptoCurrencyListings(page)
                .collect { result ->
                    when (result) {
                        is Resource.Error -> {
                            state = state.copy(
                                isLoading = false
                            )
                            _uiEvent.send(UiEvent.ShowSnackBar(
                                result.message ?: "Unknown Error"
                            ))
                        }
                        is Resource.Loading -> {
                            state = state.copy(
                                isLoading = true
                            )
                        }
                        is Resource.Success -> {
                            result.data?.let { listings ->
                                if(isActive){
                                    state = state.copy(
                                        isLoading = false,
                                        currenciesList = state.currenciesList + listings,
                                        currentPage = state.currentPage + 1,
                                        atTheEnd = listings.isEmpty()
                                    )
                                }

                            }
                        }
                    }
                }
        }
    }
}
