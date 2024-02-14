package com.hocheol.imageextraction

import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit

object RetrofitManager {

    private const val BASE_URL = "https://api.unsplash.com/"
    private const val AUTHORIZATION_HEADER = "Authorization"
    private const val CLIENT_ID = "6SVQQneXhKCzEFGL7IF_x7M9T1ydHmv82fHL0J04zTo"

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(5, TimeUnit.SECONDS)
        .readTimeout(5, TimeUnit.SECONDS)
        .writeTimeout(5, TimeUnit.SECONDS)
        .addInterceptor(AuthorizationInterceptor())
        .build()

    private val gson = GsonBuilder().setLenient().create()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .client(okHttpClient)
        .build()

    val imageService: ImageService by lazy { retrofit.create(ImageService::class.java) }

    private class AuthorizationInterceptor : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {
            val request = chain.request().newBuilder()
                .addHeader(AUTHORIZATION_HEADER, "Client-ID $CLIENT_ID")
                .build()
            return chain.proceed(request)
        }
    }
}