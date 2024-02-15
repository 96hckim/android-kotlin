package com.hocheol.mediasearch

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

    private const val BASE_URL = "https://dapi.kakao.com/v2/search/"
    private const val DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX"
    private const val AUTHORIZATION_HEADER = "Authorization"
    private const val KAKAO_AK = "a0d3971831e7a4dddab4859d46288c25"

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(5, TimeUnit.SECONDS)
        .readTimeout(5, TimeUnit.SECONDS)
        .writeTimeout(5, TimeUnit.SECONDS)
        .addInterceptor(AuthorizationInterceptor())
        .build()

    private val gson = GsonBuilder()
        .setLenient()
        .setDateFormat(DATE_FORMAT)
        .create()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .client(okHttpClient)
        .build()

    val searchService: SearchService by lazy { retrofit.create(SearchService::class.java) }

    class AuthorizationInterceptor : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {
            val request = chain.request().newBuilder()
                .addHeader(AUTHORIZATION_HEADER, "KakaoAK $KAKAO_AK")
                .build()
            return chain.proceed(request)
        }
    }
}