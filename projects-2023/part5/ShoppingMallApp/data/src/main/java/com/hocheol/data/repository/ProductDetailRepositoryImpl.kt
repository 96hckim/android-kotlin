package com.hocheol.data.repository

import com.hocheol.data.datasource.ProductDataSource
import com.hocheol.data.db.dao.BasketDao
import com.hocheol.data.db.entity.toBasketProductEntity
import com.hocheol.domain.model.Product
import com.hocheol.domain.repository.ProductDetailRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ProductDetailRepositoryImpl @Inject constructor(
    private val productDataSource: ProductDataSource,
    private val basketDao: BasketDao
) : ProductDetailRepository {

    override fun getProductDetail(productId: String): Flow<Product> {
        return productDataSource.getHomeComponents().map { products ->
            products.filterIsInstance(Product::class.java).first { it.productId == productId }
        }
    }

    override suspend fun addBasket(product: Product) {
        val existsProduct = basketDao.get(product.productId)

        if (existsProduct == null) {
            basketDao.insert(product.toBasketProductEntity())
        } else {
            basketDao.insert(existsProduct.copy(count = existsProduct.count + 1))
        }
    }
}