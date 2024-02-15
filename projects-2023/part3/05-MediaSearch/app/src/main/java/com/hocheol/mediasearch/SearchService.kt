package com.hocheol.mediasearch

import com.hocheol.mediasearch.model.ImageListResponse
import com.hocheol.mediasearch.model.VideoListResponse
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {

    @GET("image")
    fun searchImage(@Query("query") query: String): Observable<ImageListResponse>

    @GET("vclip")
    fun searchVideo(@Query("query") query: String): Observable<VideoListResponse>
}