package com.hocheol.airbnb

import retrofit2.Call
import retrofit2.http.GET

interface HouseService {

    @GET("/v3/1764ff0b-2be3-415b-9968-49d11ecb75bb")
    fun getHouseList(): Call<HouseDto>

}