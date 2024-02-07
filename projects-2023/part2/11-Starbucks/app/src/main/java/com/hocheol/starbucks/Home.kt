package com.hocheol.starbucks

data class Home(
    val user: User,
    val appBarImage: String
)

data class User(
    val nickname: String,
    val starCount: Int,
    val totalCount: Int
)