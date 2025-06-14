package com.thmanyah.thmanyahdemo.ui.utils

import com.thmanyah.domain.models.ContentItem
import com.thmanyah.domain.models.HomeResponse
import com.thmanyah.thmanyahdemo.ui.models.home.HomeSectionUiModel
import com.thmanyah.thmanyahdemo.ui.models.home.HomeUiModel
import com.thmanyah.thmanyahdemo.ui.models.home.ItemBigSquareData
import com.thmanyah.thmanyahdemo.ui.models.home.ItemQueueData
import com.thmanyah.thmanyahdemo.ui.models.home.ItemSquareData
import com.thmanyah.thmanyahdemo.ui.models.home.ItemTwoLinesGridData


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
        duration = it.duration ?: 0,
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


fun HomeResponse.toUiModel(): HomeUiModel {
    val sectionUiModels = sections?.mapNotNull { section ->
        when {
            section.contentType == "podcast" && section.type == "square" -> {
                val items = section.content
                    ?.filterIsInstance<ContentItem.Podcast>()
                    ?.mapPodcastTOSquareItem()
                items?.let { HomeSectionUiModel.Square(it, section.name) }
            }

            section.contentType == "episode" && section.type == "2_lines_grid" -> {
                val items = section.content
                    ?.filterIsInstance<ContentItem.Episode>()
                    ?.mapEpisodeTOTwoLinesGrid()
                items?.let { HomeSectionUiModel.TwoLinesGrid(it, section.name) }
            }

            section.contentType == "audio_book" &&
                    (section.type == "big_square" || section.type == "big square") -> {
                val items = section.content
                    ?.filterIsInstance<ContentItem.AudioBook>()
                    ?.mapAudioBookTOBigSquare()
                items?.let { HomeSectionUiModel.BigSquare(it, section.name) }
            }

            section.contentType == "audio_article" && section.type == "square" -> {
                val items = section.content
                    ?.filterIsInstance<ContentItem.AudioArticle>()
                    ?.mapAudioArticleTOSquareItem()
                items?.let { HomeSectionUiModel.Square(it, section.name) }
            }

            section.contentType == "podcast" && section.type == "queue" -> {
                val items = section.content
                    ?.filterIsInstance<ContentItem.Podcast>()
                    ?.mapPodcastToQueueItem()
                items?.let { HomeSectionUiModel.Queue(it, section.name) }
            }

            section.contentType == "episode" &&
                    (section.type == "big_square" || section.type == "big square") -> {
                val items = section.content
                    ?.filterIsInstance<ContentItem.Episode>()
                    ?.mapEpisodeTOBigSquare()
                items?.let { HomeSectionUiModel.BigSquare(it, section.name) }
            }

            section.contentType == "audio_book" && section.type == "2_lines_grid" -> {
                val items = section.content
                    ?.filterIsInstance<ContentItem.AudioBook>()
                    ?.mapAudioBookTOTwoLinesGrid()
                items?.let { HomeSectionUiModel.TwoLinesGrid(it, section.name) }
            }

            else -> {
                val items = section.content
                    ?.filterIsInstance<ContentItem.Podcast>()
                    ?.mapPodcastTOSquareItem()
                items?.let { HomeSectionUiModel.Square(it, section.name) }
            }
        }
    }.orEmpty()

    return HomeUiModel(sections = sectionUiModels)
}
