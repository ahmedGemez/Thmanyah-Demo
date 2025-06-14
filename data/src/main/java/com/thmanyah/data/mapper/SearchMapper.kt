package com.thmanyah.data.mapper

import com.thmanyah.data.models.HomeResponseDto
import com.thmanyah.data.models.SearchResponseDto
import com.thmanyah.data.models.SectionDto
import com.thmanyah.domain.models.HomeResponse
import com.thmanyah.domain.models.SearchResponse
import com.thmanyah.domain.models.Section

fun SearchResponseDto.toDomain(): SearchResponse {
    return SearchResponse(
        sections = sections?.map { it.toDomain() },
    )
}

