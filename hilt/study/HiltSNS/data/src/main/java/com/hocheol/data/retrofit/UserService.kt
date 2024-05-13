package com.hocheol.data.retrofit

import com.hocheol.data.model.CommonResponse
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface UserService {

    @Headers("Content-Type:application/json; charset=UTF8")
    @POST("users/login")
    suspend fun login(
        @Body requestBody: RequestBody
    ): CommonResponse<String>
}