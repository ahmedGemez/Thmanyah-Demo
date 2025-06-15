package com.thmanyah.thmanyahdemo.ui.utils

import com.thmanyah.domain.models.SearchResponse
import com.thmanyah.thmanyahdemo.ui.models.home.HomeUiModel


fun SearchResponse.toUiModel(): HomeUiModel {
    val sectionUiModels = sections
        ?.mapNotNull { it.toUiModelOrNull() }
        .orEmpty()

    return HomeUiModel(
        sections = sectionUiModels,
    )
}