package com.thmanyah.domain.models

sealed class ContentItem {
    data class Podcast(
        val podcastId: String? = null,
        val name: String? = null,
        val description: String? = null,
        val avatarUrl: String? = null,
        val episodeCount: Int? = null,
        val duration: Int? = null,
        val language: String? = null,
        val priority: Int? = null,
        val popularityScore: Int? = null,
        val score: Float? = null
    ) : ContentItem()

    data class Episode(
        val podcastPopularityScore: Int? = null,
        val podcastPriority: Int? = null,
        val episodeId: String? = null,
        val name: String? = null,
        val seasonNumber: Int? = null,
        val episodeType: String? = null,
        val podcastName: String? = null,
        val authorName: String? = null,
        val description: String? = null,
        val number: Int? = null,
        val duration: Int? = null,
        val avatarUrl: String? = null,
        val separatedAudioUrl: String? = null,
        val audioUrl: String? = null,
        val releaseDate: String? = null,
        val podcastId: String? = null,
        val chapters: List<Any>? = null,
        val paidIsEarlyAccess: Boolean? = null,
        val paidIsNowEarlyAccess: Boolean? = null,
        val paidIsExclusive: Boolean? = null,
        val paidTranscriptUrl: String? = null,
        val freeTranscriptUrl: String? = null,
        val paidIsExclusivePartially: Boolean? = null,
        val paidExclusiveStartTime: Int? = null,
        val paidEarlyAccessDate: String? = null,
        val paidEarlyAccessAudioUrl: String? = null,
        val paidExclusivityType: String? = null,
        val score: Float? = null
    ) : ContentItem()

    data class AudioBook(
        val audiobookId: String? = null,
        val name: String? = null,
        val authorName: String? = null,
        val description: String? = null,
        val avatarUrl: String? = null,
        val duration: Int? = null,
        val language: String? = null,
        val releaseDate: String? = null,
        val score: Float? = null
    ) : ContentItem()

    data class AudioArticle(
        val articleId: String? = null,
        val name: String? = null,
        val authorName: String? = null,
        val description: String? = null,
        val avatarUrl: String? = null,
        val duration: Int? = null,
        val releaseDate: String? = null,
        val score: Float? = null
    ) : ContentItem()
}
