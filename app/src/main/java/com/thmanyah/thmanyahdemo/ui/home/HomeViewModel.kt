package com.thmanyah.thmanyahdemo.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thmanyah.data.di.IODispatcher
import com.thmanyah.domain.usecases.GetHomeDataUseCase
import com.thmanyah.thmanyahdemo.ui.models.home.HomeUiModel
import com.thmanyah.thmanyahdemo.ui.models.UiState
import com.thmanyah.thmanyahdemo.ui.utils.toUiModel
import com.thmanyah.thmanyahdemo.ui.utils.toUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getHomeDataUseCase: GetHomeDataUseCase,
    @IODispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _homeData = MutableStateFlow<UiState<HomeUiModel>>(UiState.Init())
    val homeData = _homeData.asStateFlow()

    init {
        getHomeData()
    }

    private fun getHomeData() {
        getHomeDataUseCase()
            .onStart { _homeData.value = UiState.Loading() }
            .onEach { _homeData.value = it.toUiModel().toUiState() }
            .catch { _homeData.value = it.toUiState() }
            .flowOn(ioDispatcher)
            .launchIn(viewModelScope)

    }
}