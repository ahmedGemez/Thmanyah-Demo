package com.thmanyah.data.mapper

import com.thmanyah.data.models.ContentItemDto
import com.thmanyah.data.models.HomeResponseDto
import com.thmanyah.data.models.PaginationDto
import com.thmanyah.data.models.SectionDto
import com.thmanyah.domain.models.ContentItem
import com.thmanyah.domain.models.ContentType
import com.thmanyah.domain.models.HomeResponse
import com.thmanyah.domain.models.Pagination
import com.thmanyah.domain.models.Section
import com.thmanyah.domain.models.SectionType

fun HomeResponseDto.toDomain(): HomeResponse {
    return HomeResponse(
        sections = sections?.map { it.toDomain() },
        pagination = pagination?.toDoamin(),
    )
}

fun SectionDto.toDomain(): Section {
    return Section(
        name = name,
        type = type?.let { typeStr ->
            when (typeStr.lowercase()) {
                "square" -> SectionType.SQUARE
                "2_lines_grid" -> SectionType.TWO_LINES_GRID
                "big_square", "big square" -> SectionType.BIG_SQUARE
                "queue" -> SectionType.QUEUE
                else -> SectionType.SQUARE // because search return unknown data
            }
        },
        contentType = contentType?.let { contentTypeStr ->
            when (contentTypeStr.lowercase()) {
                "podcast" -> ContentType.PODCAST
                "episode" -> ContentType.EPISODE
                "audio_book" -> ContentType.AUDIO_BOOK
                "audio_article" -> ContentType.AUDIO_ARTICLE
                else ->  ContentType.PODCAST // because search return unknown data
            }
        },
        order = order?.toIntOrNull(),
        content = content?.map { it.toDomain() }
    )
}

fun ContentItemDto.toDomain(): ContentItem {
    return when (this) {
        is ContentItemDto.PodcastDto -> ContentItem.Podcast(
            podcastId = podcastId,
            name = name,
            description = description,
            avatarUrl = avatarUrl,
            episodeCount = episodeCount,
            duration = duration?.toIntOrNull(),
            language = language,
            priority = priority?.toIntOrNull(),
            popularityScore = popularityScore?.toIntOrNull(),
            score = score?.toFloatOrNull()
        )
        is ContentItemDto.EpisodeDto -> ContentItem.Episode(
            podcastPopularityScore = podcastPopularityScore,
            podcastPriority = podcastPriority,
            episodeId = episodeId,
            name = name,
            seasonNumber = seasonNumber,
            episodeType = episodeType,
            podcastName = podcastName,
            authorName = authorName,
            description = description,
            number = number,
            duration = duration,
            avatarUrl = avatarUrl,
            separatedAudioUrl = separatedAudioUrl,
            audioUrl = audioUrl,
            releaseDate = releaseDate,
            podcastId = podcastId,
            chapters = chapters,
            paidIsEarlyAccess = paidIsEarlyAccess,
            paidIsNowEarlyAccess = paidIsNowEarlyAccess,
            paidIsExclusive = paidIsExclusive,
            paidTranscriptUrl = paidTranscriptUrl,
            freeTranscriptUrl = freeTranscriptUrl,
            paidIsExclusivePartially = paidIsExclusivePartially,
            paidExclusiveStartTime = paidExclusiveStartTime,
            paidEarlyAccessDate = paidEarlyAccessDate,
            paidEarlyAccessAudioUrl = paidEarlyAccessAudioUrl,
            paidExclusivityType = paidExclusivityType,
            score = score
        )
        is ContentItemDto.AudioBookDto -> ContentItem.AudioBook(
            audiobookId = audiobookId,
            name = name,
            authorName = authorName,
            description = description,
            avatarUrl = avatarUrl,
            duration = duration,
            language = language,
            releaseDate = releaseDate,
            score = score
        )
        is ContentItemDto.AudioArticleDto -> ContentItem.AudioArticle(
            articleId = articleId,
            name = name,
            authorName = authorName,
            description = description,
            avatarUrl = avatarUrl,
            duration = duration,
            releaseDate = releaseDate,
            score = score
        )
    }
}

fun PaginationDto.toDoamin() =
  Pagination(
        nextPage = nextPage,
        totalPages = totalPages,
    )
