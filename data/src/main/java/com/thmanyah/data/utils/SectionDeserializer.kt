package com.thmanyah.data.utils

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.thmanyah.data.models.Constants.CONTENT
import com.thmanyah.data.models.Constants.CONTENT_TYPE
import com.thmanyah.data.models.Constants.NAME
import com.thmanyah.data.models.Constants.ORDER
import com.thmanyah.data.models.Constants.TYPE
import com.thmanyah.data.models.ContentItemDto
import com.thmanyah.data.models.ContentType
import com.thmanyah.data.models.SectionDto
import java.lang.reflect.Type

class SectionDeserializer : JsonDeserializer<SectionDto> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): SectionDto {
        val jsonObject = json.asJsonObject

        val contentType = jsonObject[CONTENT_TYPE]?.asString
        val contentArray = jsonObject[CONTENT]?.asJsonArray

        val content: List<ContentItemDto>? = contentArray?.mapNotNull { item ->
            when (contentType) {
                ContentType.podcast.name -> context.deserialize<ContentItemDto.PodcastDto>(
                    item,
                    ContentItemDto.PodcastDto::class.java
                )

                ContentType.episode.name -> context.deserialize<ContentItemDto.EpisodeDto>(
                    item,
                    ContentItemDto.EpisodeDto::class.java
                )

                ContentType.audio_book.name -> context.deserialize<ContentItemDto.AudioBookDto>(
                    item,
                    ContentItemDto.AudioBookDto::class.java
                )

                ContentType.audio_article.name -> context.deserialize<ContentItemDto.AudioArticleDto>(
                    item,
                    ContentItemDto.AudioArticleDto::class.java
                )
                // make else return podcast because the contentType unknown in search API
                else -> context.deserialize<ContentItemDto.PodcastDto>(
                    item,
                    ContentItemDto.PodcastDto::class.java
                )
            }
        }

        return SectionDto(
            name = jsonObject[NAME]?.asString,
            type = jsonObject[TYPE]?.asString,
            order = jsonObject[ORDER]?.asString,
            contentType = contentType,
            content = content
        )
    }
}
