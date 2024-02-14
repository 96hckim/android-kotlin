package com.hocheol.imageextraction.mvp.repository

import com.hocheol.imageextraction.ImageResponse
import com.hocheol.imageextraction.RetrofitManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ImageRepositoryImpl : ImageRepository {
    override fun getRandomImage(callback: ImageRepository.Callback) {
        RetrofitManager.imageService.getRandomImage()
            .enqueue(object : Callback<ImageResponse> {
                override fun onResponse(call: Call<ImageResponse>, response: Response<ImageResponse>) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            callback.loadImage(it.urls.regular, it.color)
                        }
                    }
                }

                override fun onFailure(call: Call<ImageResponse>, t: Throwable) {
                    t.printStackTrace()
                }
            })
    }
}