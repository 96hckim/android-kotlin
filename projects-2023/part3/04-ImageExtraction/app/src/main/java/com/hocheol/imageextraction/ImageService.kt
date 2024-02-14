package com.hocheol.imageextraction

import retrofit2.Call
import retrofit2.http.GET

interface ImageService {

    @GET("photos/random")
    fun getRandomImage(): Call<ImageResponse>
}