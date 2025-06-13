package com.thmanyah.data.models

import com.google.gson.annotations.SerializedName

data class HomeResponseDto(
    @SerializedName("sections")
    val sections: List<SectionDto>? = null,
    @SerializedName("next_page")
    val nextPage: List<SectionDto>? = null,
    @SerializedName("total_pages")
    val totalPages: Int? = null,
) 