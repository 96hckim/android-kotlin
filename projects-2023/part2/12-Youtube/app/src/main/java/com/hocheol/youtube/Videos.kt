package com.hocheol.youtube

import com.google.gson.annotations.SerializedName

data class Videos(
    @SerializedName("videos")
    val videos: List<Video>
)

data class Video(
    @SerializedName("id")
    val id: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("sources")
    val videoUrl: String,
    @SerializedName("channelName")
    val channelName: String,
    @SerializedName("viewCount")
    val viewCount: String,
    @SerializedName("dateText")
    val dateText: String,
    @SerializedName("channelThumb")
    val channelThumb: String,
    @SerializedName("thumb")
    val videoThumb: String,
    @SerializedName("title")
    val title: String
)