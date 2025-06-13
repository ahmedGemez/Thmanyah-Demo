package com.thmanyah.domain.models

data class Section(
    val name: String? = null,
    val type: String? = null,
    val contentType: String? = null,
    val order: Int? = null,
    val content: List<ContentItem>? = null
) 