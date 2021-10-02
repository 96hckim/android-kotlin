package com.hocheol.usedtrade.home

data class ArticleModel(
    val sellerId: String,
    val title: String,
    val createAt: Long,
    val content: String,
    val imageUrlList: List<String>
) {

    constructor() : this("", "", 0, "", listOf())

}
