package com.thmanyah.thmanyahdemo.ui.utils

import com.thmanyah.domain.models.ContentItem
import com.thmanyah.thmanyahdemo.ui.models.ItemBigSquareData
import com.thmanyah.thmanyahdemo.ui.models.ItemQueueData
import com.thmanyah.thmanyahdemo.ui.models.ItemSquareData
import com.thmanyah.thmanyahdemo.ui.models.ItemTwoLinesGridData


fun List<ContentItem.Podcast>.mapPodcastTOSquareItem() = map {
    ItemSquareData(
        imageUrl = it.avatarUrl ?: "",
        title = it.name ?: "",
        duration = it.duration ?: 0
    )
}

fun List<ContentItem.Episode>.mapEpisodeTOTwoLinesGrid() = map {
    ItemTwoLinesGridData(
        imageUrl = it.avatarUrl ?: "",
        title = it.name ?: "",
        duration = it.duration ?: 0,
        releaseDate = it.releaseDate ?: ""
    )
}


fun List<ContentItem.AudioBook>.mapAudioBookTOBigSquare() = map {
    ItemBigSquareData(
        imageUrl = it.avatarUrl ?: "",
        title = it.name ?: "",
    )
}

fun List<ContentItem.AudioArticle>.mapAudioArticleTOSquareItem() = map {
    ItemSquareData(
        imageUrl = it.avatarUrl ?: "",
        title = it.name ?: "",
        duration = it.duration ?: 0
    )
}

fun List<ContentItem.Podcast>.mapPodcastToQueueItem() = map {
    ItemQueueData(
        title = it.name ?: "",
        duration = it.duration?: 0,
        image = it.avatarUrl ?: ""
    )
}

fun List<ContentItem.Episode>.mapEpisodeTOBigSquare() = map {
    ItemBigSquareData(
        imageUrl = it.avatarUrl ?: "",
        title = it.name ?: "",
    )
}


fun List<ContentItem.AudioBook>.mapAudioBookTOTwoLinesGrid() = map {
    ItemTwoLinesGridData(
        imageUrl = it.avatarUrl ?: "",
        title = it.name ?: "",
        duration = it.duration ?: 0,
        releaseDate = it.releaseDate ?: ""
    )
}
