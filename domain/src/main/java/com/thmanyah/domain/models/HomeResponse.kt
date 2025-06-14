package com.thmanyah.domain.models

data class HomeResponse(
    val sections: List<Section>? = null,
    val pagination: Pagination? = null
)
