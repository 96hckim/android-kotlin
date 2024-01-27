package com.hocheol.newsapp

data class NewsModel(
    val title: String,
    val link: String,
    var imageUrl: String? = null
)

fun List<NewsItem>.transform(): List<NewsModel> {
    return this.map { newsItem ->
        NewsModel(
            title = newsItem.title.orEmpty(),
            link = newsItem.link.orEmpty(),
            imageUrl = null
        )
    }
}
