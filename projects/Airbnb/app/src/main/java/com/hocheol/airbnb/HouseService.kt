package com.hocheol.airbnb

import retrofit2.Call
import retrofit2.http.GET

interface HouseService {

    @GET("/v3/fadaabcb-5408-4146-87c0-328cc39c3507")
    fun getHouseList(): Call<HouseDto>

}