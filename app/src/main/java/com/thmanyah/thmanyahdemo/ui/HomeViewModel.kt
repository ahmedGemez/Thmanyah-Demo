package com.thmanyah.thmanyahdemo.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thmanyah.domain.usecases.GetHomeDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getHomeDataUseCase: GetHomeDataUseCase,
) : ViewModel() {
    fun getHomeData() {
        getHomeDataUseCase()
            .onEach {
                Timber.e("Sectiiiions >> ${it.sections}")
            }
            .catch {}
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)

    }


}