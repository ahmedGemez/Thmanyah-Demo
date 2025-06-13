package com.thmanyah.data.models

import com.google.gson.annotations.SerializedName

data class SectionDto(
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("type")
    val type: String? = null,
    @SerializedName("content_type")
    val contentType: String? = null,
    @SerializedName("order")
    val order: Int? = null,
    @SerializedName("content")
    val content: List<ContentItemDto>? = null
) 