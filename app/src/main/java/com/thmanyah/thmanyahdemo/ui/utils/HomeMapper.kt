package com.thmanyah.thmanyahdemo.ui.utils

import com.thmanyah.domain.ext.getRelativeTimeData
import com.thmanyah.domain.models.ContentItem
import com.thmanyah.domain.models.ContentType
import com.thmanyah.domain.models.HomeResponse
import com.thmanyah.domain.models.Pagination
import com.thmanyah.domain.models.Section
import com.thmanyah.domain.models.SectionType
import com.thmanyah.thmanyahdemo.ui.models.home.HomeSectionUiModel
import com.thmanyah.thmanyahdemo.ui.models.home.HomeUiModel
import com.thmanyah.thmanyahdemo.ui.models.home.ItemBigSquareData
import com.thmanyah.thmanyahdemo.ui.models.home.ItemQueueData
import com.thmanyah.thmanyahdemo.ui.models.home.ItemSquareData
import com.thmanyah.thmanyahdemo.ui.models.home.ItemTwoLinesGridData
import com.thmanyah.thmanyahdemo.ui.models.home.PaginationUiModel
import java.util.UUID
import kotlin.text.Typography.section


fun List<ContentItem.Podcast>.mapPodcastTOSquareItem() = map {
    ItemSquareData(
        id = UUID.randomUUID().toString(),
        imageUrl = it.avatarUrl ?: "",
        title = it.name ?: "",
        duration = it.duration ?: 0
    )
}

fun List<ContentItem.Episode>.mapEpisodeTOTwoLinesGrid() = map {
    ItemTwoLinesGridData(
        id = UUID.randomUUID().toString(),
        imageUrl = it.avatarUrl ?: "",
        title = it.name ?: "",
        duration = it.duration ?: 0,
        releaseDate = it.releaseDate?.getRelativeTimeData() ?: emptyList()
    )
}


fun List<ContentItem.AudioBook>.mapAudioBookTOBigSquare() = map {
    ItemBigSquareData(
        id = UUID.randomUUID().toString(),
        imageUrl = it.avatarUrl ?: "",
        title = it.name ?: "",
    )
}

fun List<ContentItem.AudioArticle>.mapAudioArticleTOSquareItem() = map {
    ItemSquareData(
        id = UUID.randomUUID().toString(),
        imageUrl = it.avatarUrl ?: "",
        title = it.name ?: "",
        duration = it.duration ?: 0
    )
}

fun List<ContentItem.Podcast>.mapPodcastToQueueItem() = map {
    ItemQueueData(
        id = UUID.randomUUID().toString(),
        title = it.name ?: "",
        duration = it.duration ?: 0,
        image = it.avatarUrl ?: ""
    )
}

fun List<ContentItem.Episode>.mapEpisodeTOBigSquare() = map {
    ItemBigSquareData(
        id = UUID.randomUUID().toString(),
        imageUrl = it.avatarUrl ?: "",
        title = it.name ?: "",
    )
}


fun List<ContentItem.AudioBook>.mapAudioBookTOTwoLinesGrid() = map {
    ItemTwoLinesGridData(
        id = UUID.randomUUID().toString(),
        imageUrl = it.avatarUrl ?: "",
        title = it.name ?: "",
        duration = it.duration ?: 0,
        releaseDate = it.releaseDate?.getRelativeTimeData() ?: emptyList()
    )
}

fun Pagination.toPaginationUiModel() = PaginationUiModel(nextPage, totalPages)


fun Section.toUiModelOrNull(): HomeSectionUiModel? {
    return when {
        contentType == ContentType.PODCAST && type == SectionType.SQUARE -> {
            content?.filterIsInstance<ContentItem.Podcast>()
                ?.mapPodcastTOSquareItem()
                ?.let { HomeSectionUiModel.Square(it, name) }
        }

        contentType == ContentType.EPISODE && type == SectionType.TWO_LINES_GRID -> {
            content?.filterIsInstance<ContentItem.Episode>()
                ?.mapEpisodeTOTwoLinesGrid()
                ?.let { HomeSectionUiModel.TwoLinesGrid(it, name) }
        }

        contentType == ContentType.AUDIO_BOOK && type == SectionType.BIG_SQUARE -> {
            content?.filterIsInstance<ContentItem.AudioBook>()
                ?.mapAudioBookTOBigSquare()
                ?.let { HomeSectionUiModel.BigSquare(it, name) }
        }

        contentType == ContentType.AUDIO_ARTICLE && type == SectionType.SQUARE -> {
            content?.filterIsInstance<ContentItem.AudioArticle>()
                ?.mapAudioArticleTOSquareItem()
                ?.let { HomeSectionUiModel.Square(it, name) }
        }

        contentType == ContentType.PODCAST && type == SectionType.QUEUE -> {
            content?.filterIsInstance<ContentItem.Podcast>()
                ?.mapPodcastToQueueItem()
                ?.let { HomeSectionUiModel.Queue(it, name) }
        }

        contentType == ContentType.EPISODE && type == SectionType.BIG_SQUARE -> {
            content?.filterIsInstance<ContentItem.Episode>()
                ?.mapEpisodeTOBigSquare()
                ?.let { HomeSectionUiModel.BigSquare(it, name) }
        }

        contentType == ContentType.AUDIO_BOOK && type == SectionType.TWO_LINES_GRID -> {
            content?.filterIsInstance<ContentItem.AudioBook>()
                ?.mapAudioBookTOTwoLinesGrid()
                ?.let { HomeSectionUiModel.TwoLinesGrid(it, name) }
        }

        else -> null
    }
}


fun HomeResponse.toUiModel(): HomeUiModel {
    val sectionUiModels = sections
        ?.mapNotNull { it.toUiModelOrNull() }
        .orEmpty()

    return HomeUiModel(
        id = UUID.randomUUID().toString(),
        sections = sectionUiModels,
        pagination = pagination?.toPaginationUiModel()
    )
}

