package com.thmanyah.data.repositoriesImpl
import com.thmanyah.data.ApiService
import com.thmanyah.data.mapper.toDomain
import com.thmanyah.data.utils.mapDataOrThrow
import com.thmanyah.domain.models.HomeResponse
import com.thmanyah.domain.repositories.HomeRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class HomeRepoImpl @Inject constructor(private val apiService: ApiService) : HomeRepo {
    override fun getHomeData(): Flow<HomeResponse> = flow {
        emit(apiService.getHomeResponse().toDomain())
    }.mapDataOrThrow()
}