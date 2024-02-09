package com.hocheol.youtube

data class Video(
    val id: String,
    val description: String,
    val sources: String,
    val channelName: String,
    val viewCount: String,
    val dateText: String,
    val channelThumb: String,
    val thumb: String,
    val title: String
)
