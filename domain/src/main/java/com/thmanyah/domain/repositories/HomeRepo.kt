package com.thmanyah.domain.repositories

import com.thmanyah.domain.models.HomeResponse
import kotlinx.coroutines.flow.Flow

interface HomeRepo {
    fun getHomeData(): Flow<HomeResponse>
}