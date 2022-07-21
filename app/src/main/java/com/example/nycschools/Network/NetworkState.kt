package com.example.nycschools.Network

sealed class NetworkState {
    data class LOADING(val isLoading: Boolean = true): NetworkState()
    data class SUCCESS<T>(val response :T): NetworkState()
    data class ERROR(val error: Exception): NetworkState()
}
