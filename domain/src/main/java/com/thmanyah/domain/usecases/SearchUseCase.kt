package com.thmanyah.domain.usecases

import com.thmanyah.domain.models.HomeResponse
import com.thmanyah.domain.models.SearchResponse
import com.thmanyah.domain.repositories.HomeRepo
import kotlinx.coroutines.flow.Flow


class SearchUseCase(
    private val homeRepo: HomeRepo
) {

    operator fun invoke(): Flow<SearchResponse> {
        return homeRepo.search()
    }
}