package com.example.moviehub.ui

sealed class UiState<out T:Any?>{
    object Loading : UiState<Nothing>()
    data class Success<out T:Any?>(val data: T) : UiState<T>()
    object Error : UiState<Nothing>()
}
