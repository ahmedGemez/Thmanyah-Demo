package com.thmanyah.thmanyahdemo.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thmanyah.data.di.IODispatcher
import com.thmanyah.domain.usecases.SearchUseCase
import com.thmanyah.thmanyahdemo.ui.models.UiState
import com.thmanyah.thmanyahdemo.ui.models.home.HomeUiModel
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
class SearchViewModel @Inject constructor(
    private val searchUseCase: SearchUseCase,
    @IODispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _searchData = MutableStateFlow<UiState<HomeUiModel>>(UiState.Init())
    val searchData = _searchData.asStateFlow()

    fun getSearchData(debouncedQuery: String) {
        searchUseCase()
            .onStart { _searchData.value = UiState.Loading() }
            .onEach {
                _searchData.value = it.toUiModel().toUiState()
            }
            .catch {
                _searchData.value = it.toUiState()
            }
            .flowOn(ioDispatcher)
            .launchIn(viewModelScope)

    }
}