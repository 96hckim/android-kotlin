package com.hocheol.domain.repository

import com.hocheol.domain.model.BasketProduct
import com.hocheol.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface BasketRepository {

    fun getBasketProducts(): Flow<List<BasketProduct>>

    suspend fun removeBasketProduct(product: Product)

    suspend fun checkoutBasket(products: List<BasketProduct>)
}