package com.hocheol.todaynotice

import com.google.gson.annotations.SerializedName

data class Message(
    @SerializedName("message")
    val message: String
)
