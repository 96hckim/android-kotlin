package com.hocheol.data.repository

import com.hocheol.data.datasource.ProductDataSource
import com.hocheol.data.db.dao.LikeDao
import com.hocheol.data.db.entity.toLikeProductEntity
import com.hocheol.domain.model.Category
import com.hocheol.domain.model.Product
import com.hocheol.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val productDataSource: ProductDataSource,
    private val likeDao: LikeDao
) : CategoryRepository {

    override fun getCategories(): Flow<List<Category>> = flow {
        emit(
            listOf(
                Category.Top, Category.Bag, Category.Outerwear,
                Category.Dress, Category.FashionAccessories, Category.Pants,
                Category.Skirt, Category.Shoes
            )
        )
    }

    override fun getProductsByCategory(category: Category): Flow<List<Product>> {
        return productDataSource.getHomeComponents().map { modelList ->
            modelList.filterIsInstance(Product::class.java)
                .filter { it.category.categoryId == category.categoryId }
        }.combine(likeDao.getAll()) { products, likeList ->
            products.map { product -> updateLikeStatus(product, likeList.map { it.productId }) }
        }
    }

    override suspend fun likeProduct(product: Product) {
        if (product.isLike) {
            likeDao.delete(product.productId)
        } else {
            likeDao.insert(product.toLikeProductEntity())
        }
    }

    private fun updateLikeStatus(product: Product, likeProductIds: List<String>): Product {
        return product.copy(isLike = likeProductIds.contains(product.productId))
    }
}