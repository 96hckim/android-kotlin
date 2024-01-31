package com.hocheol.weatherapp

import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

data class BaseDateTime(
    val baseDate: String,
    val baseTime: String
) {
    companion object {
        fun getBaseDateTime(): BaseDateTime {
            var dateTime = LocalDateTime.now()

            val currentTime = dateTime.toLocalTime()
            val baseTime = when {
                currentTime < LocalTime.of(2, 30) -> {
                    dateTime = dateTime.minusDays(1)
                    "2300"
                }

                currentTime < LocalTime.of(5, 30) -> "0200"
                currentTime < LocalTime.of(8, 30) -> "0500"
                currentTime < LocalTime.of(11, 30) -> "0800"
                currentTime < LocalTime.of(14, 30) -> "1100"
                currentTime < LocalTime.of(17, 30) -> "1400"
                currentTime < LocalTime.of(20, 30) -> "1700"
                currentTime < LocalTime.of(23, 30) -> "2000"
                else -> "2300"
            }

            val dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd")
            val baseDate = dateTime.format(dateFormatter)

            return BaseDateTime(baseDate, baseTime)
        }
    }
}
