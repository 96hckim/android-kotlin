package com.hocheol.unsplash.data

import com.hocheol.unsplash.BuildConfig
import com.hocheol.unsplash.data.models.PhotoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface UnsplashApiService {

    @Headers("Authorization: Client-ID ${BuildConfig.UNSPLASH_ACCESS_KEY}")
    @GET(
        "/photos/random?" +
                "&count=30"
    )
    suspend fun getRandomPhotos(
        @Query("query") query: String?,
    ): Response<List<PhotoResponse>>

}