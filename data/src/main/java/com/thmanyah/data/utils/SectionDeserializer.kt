package com.thmanyah.data.utils

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.thmanyah.data.models.ContentItemDto
import com.thmanyah.data.models.SectionDto
import java.lang.reflect.Type

class SectionDeserializer : JsonDeserializer<SectionDto> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): SectionDto {
        val jsonObject = json.asJsonObject

        val contentType = jsonObject["content_type"]?.asString
        val contentArray = jsonObject["content"]?.asJsonArray

        val content: List<ContentItemDto>? = contentArray?.mapNotNull { item ->
            when (contentType) {
                "podcast" -> context.deserialize<ContentItemDto.PodcastDto>(item, ContentItemDto.PodcastDto::class.java)
                "episode" -> context.deserialize<ContentItemDto.EpisodeDto>(item, ContentItemDto.EpisodeDto::class.java)
                "audio_book" -> context.deserialize<ContentItemDto.AudioBookDto>(item, ContentItemDto.AudioBookDto::class.java)
                "audio_article" -> context.deserialize<ContentItemDto.AudioArticleDto>(item, ContentItemDto.AudioArticleDto::class.java)
                else -> null
            }
        }

        return SectionDto(
            name = jsonObject["name"]?.asString,
            type = jsonObject["type"]?.asString,
            contentType = contentType,
            order = jsonObject["order"]?.asInt,
            content = content
        )
    }
}
