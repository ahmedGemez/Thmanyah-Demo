package com.thmanyah.thmanyahdemo.ui.models.home

import java.util.UUID


sealed class HomeSectionUiModel {
    val id = UUID.randomUUID()

    data class Square(val items: List<ItemSquareData> = emptyList(), val name: String? = null) :
        HomeSectionUiModel()

    data class TwoLinesGrid(
        val items: List<ItemTwoLinesGridData> = emptyList(),
        val name: String? = null
    ) :
        HomeSectionUiModel()

    data class BigSquare(val items: List<ItemBigSquareData> = emptyList(), val name: String? = null) :
        HomeSectionUiModel()

    data class Queue(val items: List<ItemQueueData> = emptyList(), val name: String? = null) :
        HomeSectionUiModel()
}