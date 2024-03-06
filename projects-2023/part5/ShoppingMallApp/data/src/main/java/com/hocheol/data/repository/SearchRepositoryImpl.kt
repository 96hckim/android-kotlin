package com.hocheol.data.repository

import com.hocheol.data.datasource.ProductDataSource
import com.hocheol.data.db.dao.SearchDao
import com.hocheol.data.db.entity.SearchKeywordEntity
import com.hocheol.data.db.entity.toDomainModel
import com.hocheol.domain.model.Product
import com.hocheol.domain.model.SearchKeyword
import com.hocheol.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val productDataSource: ProductDataSource,
    private val searchDao: SearchDao
) : SearchRepository {

    override suspend fun search(searchKeyword: SearchKeyword): Flow<List<Product>> {
        searchDao.insert(SearchKeywordEntity(searchKeyword.keyword))
        return productDataSource.getProducts().map { products ->
            products.filter { product ->
                product.productName.contains(searchKeyword.keyword)
            }
        }
    }

    override fun getSearchKeywords(): Flow<List<SearchKeyword>> {
        return searchDao.getAll().map { it.map { entity -> entity.toDomainModel() } }
    }
}