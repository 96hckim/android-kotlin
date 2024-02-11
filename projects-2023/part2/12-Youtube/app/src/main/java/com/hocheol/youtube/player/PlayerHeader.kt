package com.hocheol.youtube.player

data class PlayerHeader(
    override val id: String,
    val description: String,
    val channelName: String,
    val viewCount: String,
    val dateText: String,
    val channelThumb: String,
    val title: String
) : PlayerVideoModel
