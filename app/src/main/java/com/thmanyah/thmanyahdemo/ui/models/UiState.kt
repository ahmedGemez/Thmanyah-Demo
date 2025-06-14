package com.thmanyah.thmanyahdemo.ui.models

sealed class UiState<T> {
    class Init<T> : UiState<T>()
    class Loading<T> : UiState<T>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error<T>(val error: ThmanyahError ) : UiState<T>()
    class Empty<T>(val msgResId: Int) : UiState<T>()
    class LoadMore<T> (val data: T): UiState<T>()
}