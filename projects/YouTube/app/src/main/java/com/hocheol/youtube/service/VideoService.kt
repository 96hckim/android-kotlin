package com.hocheol.youtube.service

import com.hocheol.youtube.dto.VideoDto
import retrofit2.Call
import retrofit2.http.GET

interface VideoService {

    @GET("/v3/3fda333f-2fe3-45ec-b371-71844e966345")
    fun listVideos(): Call<VideoDto>

}