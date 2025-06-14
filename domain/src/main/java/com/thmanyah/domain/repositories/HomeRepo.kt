package com.thmanyah.domain.repositories

import com.thmanyah.domain.models.HomeResponse
import com.thmanyah.domain.models.SearchResponse
import kotlinx.coroutines.flow.Flow

interface HomeRepo {
    fun getHomeData(): Flow<HomeResponse>
    fun search(): Flow<SearchResponse>
}