package com.thmanyah.thmanyahdemo.ui.utils

import com.thmanyah.domain.models.ContentItem
import com.thmanyah.domain.models.HomeResponse
import com.thmanyah.domain.models.SearchResponse
import com.thmanyah.thmanyahdemo.ui.models.home.HomeSectionUiModel
import com.thmanyah.thmanyahdemo.ui.models.home.HomeUiModel

fun SearchResponse.toUiModel(): HomeUiModel {
    val sectionUiModels = sections?.mapNotNull { section ->
        val items = section.content
            ?.filterIsInstance<ContentItem.Podcast>()
            ?.mapPodcastTOSquareItem()
        items?.let { HomeSectionUiModel.Square(it, section.name) }

    }.orEmpty()

    return HomeUiModel(sections = sectionUiModels)
}