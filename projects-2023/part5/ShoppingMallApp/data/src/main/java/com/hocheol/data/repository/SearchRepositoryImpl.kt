package com.hocheol.data.repository

import com.hocheol.data.datasource.ProductDataSource
import com.hocheol.data.db.dao.LikeDao
import com.hocheol.data.db.dao.SearchDao
import com.hocheol.data.db.entity.SearchKeywordEntity
import com.hocheol.data.db.entity.toDomainModel
import com.hocheol.data.db.entity.toLikeProductEntity
import com.hocheol.domain.model.Product
import com.hocheol.domain.model.SearchFilter
import com.hocheol.domain.model.SearchKeyword
import com.hocheol.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val productDataSource: ProductDataSource,
    private val searchDao: SearchDao,
    private val likeDao: LikeDao
) : SearchRepository {

    override suspend fun search(searchKeyword: SearchKeyword, filters: List<SearchFilter>): Flow<List<Product>> {
        searchDao.insert(SearchKeywordEntity(searchKeyword.keyword))
        return productDataSource.getProducts().map { products ->
            products.filter { product ->
                isAvailableProduct(product, searchKeyword, filters)
            }
        }.combine(likeDao.getAll()) { products, likeList ->
            products.map { product -> updateLikeStatus(product, likeList.map { it.productId }) }
        }
    }

    private fun isAvailableProduct(product: Product, searchKeyword: SearchKeyword, filters: List<SearchFilter>): Boolean {
        val isProductAvailable = filters.all { it.isAvailableProduct(product) }
        val isProductNameMatched = product.productName.contains(searchKeyword.keyword)
        return isProductAvailable && isProductNameMatched
    }

    override fun getSearchKeywords(): Flow<List<SearchKeyword>> {
        return searchDao.getAll().map { it.map { entity -> entity.toDomainModel() } }
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