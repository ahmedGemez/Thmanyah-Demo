package com.thmanyah.domain.usecases

import com.thmanyah.domain.models.HomeResponse
import com.thmanyah.domain.repositories.HomeRepo
import kotlinx.coroutines.flow.Flow

class GetHomeDataUseCase(
    private val homeRepo: HomeRepo
) {

     operator fun invoke(): Flow<HomeResponse> {
        return homeRepo.getHomeData()
    }
} 