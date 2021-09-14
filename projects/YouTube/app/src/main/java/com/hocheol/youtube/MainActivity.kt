package com.hocheol.youtube

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.hocheol.youtube.dto.VideoDto
import com.hocheol.youtube.service.VideoService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, PlayerFragment())
            .commit()

        getVideoList()

    }

    private fun getVideoList() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://run.mocky.io/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(VideoService::class.java).also { videoService ->
            videoService.listVideos()
                .enqueue(object : Callback<VideoDto> {

                    override fun onResponse(call: Call<VideoDto>, response: Response<VideoDto>) {
                        if (response.isSuccessful.not()) {
                            Log.d(TAG, "response fail")
                            return
                        }

                        response.body()?.let { videoDto ->
                            Log.d(TAG, videoDto.toString())
                        }
                    }

                    override fun onFailure(call: Call<VideoDto>, t: Throwable) {
                    }

                })
        }
    }

    companion object {
        private const val TAG = "MainActivity"
    }

}