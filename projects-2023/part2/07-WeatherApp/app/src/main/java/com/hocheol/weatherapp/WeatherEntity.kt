package com.hocheol.weatherapp

import com.google.gson.annotations.SerializedName

data class WeatherEntity(
    @SerializedName("response")
    val response: WeatherResponse
)

data class WeatherResponse(
    @SerializedName("header")
    val header: WeatherHeader,
    @SerializedName("body")
    val body: WeatherBody
)

data class WeatherHeader(
    @SerializedName("resultCode")
    val resultCode: String,
    @SerializedName("resultMsg")
    val resultMessage: String
)

data class WeatherBody(
    @SerializedName("dataType")
    val dataType: String,
    @SerializedName("items")
    val items: ForecastEntityList,
    @SerializedName("numOfRows")
    val numOfRows: Int,
    @SerializedName("pageNo")
    val pageNo: Int,
    @SerializedName("totalCount")
    val totalCount: Int
)

data class ForecastEntityList(
    @SerializedName("item")
    val forecastEntities: List<ForecastEntity>
)

data class ForecastEntity(
    @SerializedName("baseDate")
    val baseDate: String,
    @SerializedName("baseTime")
    val baseTime: String,
    @SerializedName("category")
    val category: Category?,
    @SerializedName("fcstDate")
    val forecastDate: String,
    @SerializedName("fcstTime")
    val forecastTime: String,
    @SerializedName("fcstValue")
    val forecastValue: String,
    @SerializedName("nx")
    val nx: Int,
    @SerializedName("ny")
    val ny: Int
)