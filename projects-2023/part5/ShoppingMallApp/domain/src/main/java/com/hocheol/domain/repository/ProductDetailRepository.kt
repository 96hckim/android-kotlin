package com.hocheol.domain.repository

import com.hocheol.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductDetailRepository {

    fun getProductDetail(productId: String): Flow<Product>

    suspend fun addBasket(product: Product)
}