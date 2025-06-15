package com.thmanyah.domain.models

data class Section(
    val name: String? = null,
    val type: SectionType? = null,
    val contentType: ContentType? = null,
    val order: Int? = null,
    val content: List<ContentItem>? = null
) 