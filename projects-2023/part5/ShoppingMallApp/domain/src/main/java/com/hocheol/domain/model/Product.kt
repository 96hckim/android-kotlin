package com.hocheol.domain.model

data class Product(
    val productId: String,
    val productName: String,
    val imageUrl: String,
    val price: Price,
    val category: Category,
    val shop: Shop,
    val isNew: Boolean,
    val isFreeShipping: Boolean,
    val isLike: Boolean,
    override val type: ModelType = ModelType.PRODUCT
) : BaseModel()
