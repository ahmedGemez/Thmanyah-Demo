package com.thmanyah.thmanyahdemo.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thmanyah.domain.models.HomeResponse
import com.thmanyah.domain.usecases.GetHomeDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getHomeDataUseCase: GetHomeDataUseCase
) : ViewModel() {

    private val _homeData = MutableStateFlow<HomeResponse?>(null)
    val homeData: StateFlow<HomeResponse?> = _homeData

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init {
        getHomeData()
    }

    fun getHomeData() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            
            getHomeDataUseCase()
                .catch { e ->
                    _error.value = e.message
                    _isLoading.value = false
                }
                .collect { response ->
                    _homeData.value = response
                    _isLoading.value = false
                }
        }
    }
}