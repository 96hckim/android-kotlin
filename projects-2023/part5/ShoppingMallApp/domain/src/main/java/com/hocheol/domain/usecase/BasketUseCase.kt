package com.hocheol.domain.usecase

import com.hocheol.domain.model.BasketProduct
import com.hocheol.domain.model.Product
import com.hocheol.domain.repository.BasketRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BasketUseCase @Inject constructor(
    private val basketRepository: BasketRepository
) {

    fun getBasketProducts(): Flow<List<BasketProduct>> {
        return basketRepository.getBasketProducts()
    }

    suspend fun removeBasketProducts(product: Product) {
        basketRepository.removeBasketProduct(product)
    }
}