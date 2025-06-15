package com.thmanyah.thmanyahdemo.ui.utils

import com.thmanyah.domain.ext.getRelativeTimeData
import com.thmanyah.domain.models.ContentItem
import com.thmanyah.domain.models.HomeResponse
import com.thmanyah.domain.models.Pagination
import com.thmanyah.domain.models.Section
import com.thmanyah.thmanyahdemo.ui.models.home.HomeSectionUiModel
import com.thmanyah.thmanyahdemo.ui.models.home.HomeUiModel
import com.thmanyah.thmanyahdemo.ui.models.home.ItemBigSquareData
import com.thmanyah.thmanyahdemo.ui.models.home.ItemQueueData
import com.thmanyah.thmanyahdemo.ui.models.home.ItemSquareData
import com.thmanyah.thmanyahdemo.ui.models.home.ItemTwoLinesGridData
import com.thmanyah.thmanyahdemo.ui.models.home.PaginationUiModel
import kotlin.text.Typography.section


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
        releaseDate = it.releaseDate?.getRelativeTimeData() ?: emptyList()
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
        releaseDate = it.releaseDate?.getRelativeTimeData() ?: emptyList()
    )
}

fun Pagination.toPaginationUiModel() = PaginationUiModel(nextPage, totalPages)


fun Section.toUiModelOrNull(): HomeSectionUiModel? {
    return when {
        contentType == "podcast" && type == "square" -> {
            content?.filterIsInstance<ContentItem.Podcast>()
                ?.mapPodcastTOSquareItem()
                ?.let { HomeSectionUiModel.Square(it, name) }
        }

        contentType == "episode" && type == "2_lines_grid" -> {
            content?.filterIsInstance<ContentItem.Episode>()
                ?.mapEpisodeTOTwoLinesGrid()
                ?.let { HomeSectionUiModel.TwoLinesGrid(it, name) }
        }

        contentType == "audio_book" && (type == "big_square" || type == "big square") -> {
            content?.filterIsInstance<ContentItem.AudioBook>()
                ?.mapAudioBookTOBigSquare()
                ?.let { HomeSectionUiModel.BigSquare(it, name) }
        }

        contentType == "audio_article" && type == "square" -> {
            content?.filterIsInstance<ContentItem.AudioArticle>()
                ?.mapAudioArticleTOSquareItem()
                ?.let { HomeSectionUiModel.Square(it, name) }
        }

        contentType == "podcast" && type == "queue" -> {
            content?.filterIsInstance<ContentItem.Podcast>()
                ?.mapPodcastToQueueItem()
                ?.let { HomeSectionUiModel.Queue(it, name) }
        }

        contentType == "episode" && (type == "big_square" || type == "big square") -> {
            content?.filterIsInstance<ContentItem.Episode>()
                ?.mapEpisodeTOBigSquare()
                ?.let { HomeSectionUiModel.BigSquare(it, name) }
        }

        contentType == "audio_book" && type == "2_lines_grid" -> {
            content?.filterIsInstance<ContentItem.AudioBook>()
                ?.mapAudioBookTOTwoLinesGrid()
                ?.let { HomeSectionUiModel.TwoLinesGrid(it, name) }
        }

        else -> {
            content?.filterIsInstance<ContentItem.Podcast>()
                ?.mapPodcastTOSquareItem()
                ?.let { HomeSectionUiModel.Square(it, name) }
        }
    }
}


fun HomeResponse.toUiModel(): HomeUiModel {
    val sectionUiModels = sections
        ?.mapNotNull { it.toUiModelOrNull() }
        .orEmpty()

    return HomeUiModel(
        sections = sectionUiModels,
        pagination = pagination?.toPaginationUiModel()
    )
}

