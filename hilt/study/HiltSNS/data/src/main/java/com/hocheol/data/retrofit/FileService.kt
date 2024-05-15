package com.hocheol.data.retrofit

import com.hocheol.data.model.CommonResponse
import com.hocheol.data.model.FileDTO
import okhttp3.MultipartBody
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface FileService {

    @Headers("ContentType: multipart/form-data;")
    @Multipart
    @POST("files")
    suspend fun uploadFile(
        @Part fileName: MultipartBody.Part,
        @Part file: MultipartBody.Part
    ): CommonResponse<FileDTO>
}