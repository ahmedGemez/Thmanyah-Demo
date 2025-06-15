package com.thmanyah.thmanyahdemo.ui.models.home


data class ItemQueueData(
    val title: String,
    val duration: Int,
    val timeAgo: List<Long>? = null,
    val image: String
)