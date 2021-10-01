package com.hocheol.usedtrade.home

data class ArticleModel(
    val sellerId: String,
    val title: String,
    val createAt: Long,
    val content: String,
    val imageUrl: String
) {

    constructor() : this("", "", 0, "", "")

}
