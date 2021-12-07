package com.hocheol.shopping.domain.product

import com.hocheol.shopping.data.entity.product.ProductEntity
import com.hocheol.shopping.data.repository.ProductRepository
import com.hocheol.shopping.domain.UseCase

internal class GetProductListUseCase(
    private val productRepository: ProductRepository
) : UseCase {

    suspend operator fun invoke(): List<ProductEntity> {
        return productRepository.getProductList()
    }

}
