package com.hocheol.shopping.data.response

data class ProductsResponse(
    val items: List<ProductResponse>,
    val count: Int
)
