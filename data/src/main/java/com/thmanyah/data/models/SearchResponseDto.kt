package com.thmanyah.data.models

import com.google.gson.annotations.SerializedName

data class SearchResponseDto(
    @SerializedName("sections")
    val sections: List<SectionDto>? = null,
)
