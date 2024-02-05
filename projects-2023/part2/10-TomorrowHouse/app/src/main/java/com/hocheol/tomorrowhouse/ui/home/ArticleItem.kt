package com.hocheol.tomorrowhouse.ui.home

data class ArticleItem(
    val articleId: String,
    val description: String,
    val imageUrl: String,
    var isBookMark: Boolean
)
