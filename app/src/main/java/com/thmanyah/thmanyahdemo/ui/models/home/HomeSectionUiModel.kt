package com.thmanyah.thmanyahdemo.ui.models.home


sealed class HomeSectionUiModel() {
    data class Square(val items: List<ItemSquareData>? = null, val name: String? = null) :
        HomeSectionUiModel()

    data class TwoLinesGrid(
        val items: List<ItemTwoLinesGridData>? = null,
        val name: String? = null
    ) :
        HomeSectionUiModel()

    data class BigSquare(val items: List<ItemBigSquareData>? = null, val name: String? = null) :
        HomeSectionUiModel()

    data class Queue(val items: List<ItemQueueData>? = null, val name: String? = null) :
        HomeSectionUiModel()
}