package com.hocheol.shopping.domain.product

import com.hocheol.shopping.data.repository.ProductRepository
import com.hocheol.shopping.domain.UseCase

internal class DeleteOrderedProductListUseCase(
    private val productRepository: ProductRepository
) : UseCase {

    suspend operator fun invoke() {
        return productRepository.deleteAll()
    }

}
