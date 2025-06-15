package com.thmanyah.thmanyahdemo.ui.models.home

import java.util.UUID

data class HomeUiModel(
    val id: String = UUID.randomUUID().toString(),
    val sections: List<HomeSectionUiModel> = emptyList() ,
    val pagination: PaginationUiModel? = null,
)