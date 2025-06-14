package com.thmanyah.thmanyahdemo.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thmanyah.data.di.IODispatcher
import com.thmanyah.domain.usecases.GetHomeDataUseCase
import com.thmanyah.thmanyahdemo.ui.models.home.HomeUiModel
import com.thmanyah.thmanyahdemo.ui.models.UiState
import com.thmanyah.thmanyahdemo.ui.models.home.HomeSectionUiModel
import com.thmanyah.thmanyahdemo.ui.utils.toUiModel
import com.thmanyah.thmanyahdemo.ui.utils.toUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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
    val homeData: StateFlow<UiState<HomeUiModel>> = _homeData.asStateFlow()

    private val _isLoadingMore = MutableStateFlow(false)
    val isLoadingMore: StateFlow<Boolean> = _isLoadingMore.asStateFlow()

    private var currentPage = 1
    private var isLastPage = false
    private var currentSections = mutableListOf<HomeSectionUiModel>()

    init {
        loadPage(1)
    }

    fun loadNextPage() {
        if (_isLoadingMore.value || isLastPage) return
        loadPage(currentPage + 1)
    }


    private fun loadPage(page: Int) {
        getHomeDataUseCase()
            .onStart {
                _isLoadingMore.value = true
                if (page == 1) {
                    _homeData.value = UiState.Loading()
                }
                else {
                    _homeData.value = UiState.LoadMore(HomeUiModel(currentSections))
                }
            }
            .onEach { if (page == 1) {
                currentSections = it.toUiModel().sections.toMutableList()
            } else {
                currentSections.addAll(it.toUiModel().sections)
            }
                _homeData.value = UiState.Success(HomeUiModel(currentSections))
                currentPage = page
                isLastPage = currentPage == it.pagination?.totalPages
                _isLoadingMore.value = false
            }
            .catch {
                _isLoadingMore.value = false
                _homeData.value = it.toUiState()
            }
            .flowOn(ioDispatcher)
            .launchIn(viewModelScope)
    }

}