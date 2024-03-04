package com.hocheol.domain.model

data class Product(
    val id: String,
    val name: String,
    val imageUrl: String,
    val price: Price,
    val category: Category,
    val shop: Shop,
    val isNew: Boolean,
    val isFreeShipping: Boolean
)
