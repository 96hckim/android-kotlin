package com.hocheol.youtube.player

import com.hocheol.youtube.Video

data class PlayerVideo(
    override val id: String,
    val description: String,
    val videoUrl: String,
    val channelName: String,
    val viewCount: String,
    val dateText: String,
    val channelThumb: String,
    val videoThumb: String,
    val title: String
) : PlayerVideoModel

fun Video.transform(): PlayerVideo {
    return PlayerVideo(
        id = id,
        description = description,
        videoUrl = videoUrl,
        channelName = channelName,
        viewCount = viewCount,
        dateText = dateText,
        channelThumb = channelThumb,
        videoThumb = videoThumb,
        title = title
    )
}