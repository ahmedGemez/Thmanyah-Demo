package com.thmanyah.domain.usecases

import com.thmanyah.domain.models.HomeResponse
import com.thmanyah.domain.repositories.HomeRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetHomeDataUseCase(
    private val homeRepo: HomeRepo
) {
    operator fun invoke(): Flow<HomeResponse> {
        return homeRepo.getHomeData()
            .map { response ->
                response.copy(
                    sections = response.sections?.sortedBy { it.order }
                )
            }
    }
} 