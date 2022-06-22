package com.example.cryptocurrencyapp.util

sealed class UiEvent {
    data class ShowSnackBar(val message: String): UiEvent()
}
