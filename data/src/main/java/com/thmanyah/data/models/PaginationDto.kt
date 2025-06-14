package com.thmanyah.data.models

import com.google.gson.annotations.SerializedName


data class PaginationDto(
    @SerializedName("next_page") val nextPage: String? = null,
    @SerializedName("total_pages") val totalPages: Int = 0
)