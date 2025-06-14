package com.thmanyah.data.mapper

import com.thmanyah.data.models.*
import com.thmanyah.domain.models.*

fun HomeResponseDto.toDomain(): HomeResponse {
    return HomeResponse(
        sections = sections?.map { it.toDomain() },
        nextPage = nextPage,
        totalPages = totalPages
    )
}

fun SectionDto.toDomain(): Section {
    return Section(
        name = name,
        type = type,
        contentType = contentType,
        order = order,
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
            duration = duration,
            language = language,
            priority = priority,
            popularityScore = popularityScore,
            score = score
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