package com.thmanyah.domain.models

data class HomeResponse(
    val sections: List<Section>? = null,
    val nextPage: String? = null,
    val totalPages: Int? = null,
)
