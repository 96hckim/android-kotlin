package com.hocheol.imageextraction

import io.reactivex.rxjava3.core.Single
import retrofit2.Call
import retrofit2.http.GET

interface ImageService {

    @GET("photos/random")
    fun getRandomImage(): Call<ImageResponse>

    @GET("photos/random")
    fun getRandomImageRx(): Single<ImageResponse>

    @GET("photos/random")
    suspend fun getRandomImageSuspend(): ImageResponse
}