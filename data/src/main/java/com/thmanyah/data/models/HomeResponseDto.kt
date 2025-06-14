package com.thmanyah.data.models

import com.google.gson.annotations.SerializedName

data class HomeResponseDto(
    @SerializedName("sections")
    val sections: List<SectionDto>? = null,
    @SerializedName("pagination")
    val pagination: PaginationDto? = null,
)