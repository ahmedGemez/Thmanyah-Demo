package com.thmanyah.data.mapper

import com.thmanyah.data.models.SearchResponseDto
import com.thmanyah.domain.models.SearchResponse

fun SearchResponseDto.toDomain(): SearchResponse {
    return SearchResponse(
        sections = sections?.map { it.toDomain() },
    )
}

