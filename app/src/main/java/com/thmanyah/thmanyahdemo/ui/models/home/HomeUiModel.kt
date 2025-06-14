package com.thmanyah.thmanyahdemo.ui.models.home

data class HomeUiModel(
    val sections: List<HomeSectionUiModel> = emptyList() ,
    val pagination: PaginationUiModel? = null,
)