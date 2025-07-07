package com.thmanyah.thmanyahdemo.ui.models.home



data class ItemQueueData(
    val id: String,
    val title: String,
    val duration: Int,
    val timeAgo: List<Long>? = null,
    val image: String
)