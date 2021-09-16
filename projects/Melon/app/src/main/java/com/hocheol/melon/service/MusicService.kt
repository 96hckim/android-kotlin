package com.hocheol.melon.service

import retrofit2.Call
import retrofit2.http.GET

interface MusicService {

    @GET("/v3/efc6ae12-a556-401a-8b96-2cf6b429fa6a")
    fun listMusics(): Call<MusicDto>

}