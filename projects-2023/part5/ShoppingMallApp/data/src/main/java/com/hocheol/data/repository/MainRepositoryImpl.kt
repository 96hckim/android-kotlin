package com.hocheol.data.repository

import com.hocheol.data.datasource.ProductDataSource
import com.hocheol.data.db.dao.LikeDao
import com.hocheol.data.db.entity.toLikeProductEntity
import com.hocheol.domain.model.BaseModel
import com.hocheol.domain.model.Carousel
import com.hocheol.domain.model.Product
import com.hocheol.domain.model.Ranking
import com.hocheol.domain.repository.MainRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val productDataSource: ProductDataSource,
    private val likeDao: LikeDao
) : MainRepository {

    override fun getModelList(): Flow<List<BaseModel>> {
        return productDataSource.getHomeComponents().combine(likeDao.getAll()) { homeComponents, likeList ->
            homeComponents.map { model -> mappingLike(model, likeList.map { it.productId }) }
        }
    }

    override suspend fun likeProduct(product: Product) {
        if (product.isLike) {
            likeDao.delete(product.productId)
        } else {
            likeDao.insert(product.toLikeProductEntity())
        }
    }

    private fun mappingLike(baseModel: BaseModel, likeProductIds: List<String>): BaseModel {
        return when (baseModel) {
            is Carousel -> {
                baseModel.copy(productList = baseModel.productList.map { updateLikeStatus(it, likeProductIds) })
            }

            is Ranking -> {
                baseModel.copy(productList = baseModel.productList.map { updateLikeStatus(it, likeProductIds) })
            }

            is Product -> {
                updateLikeStatus(baseModel, likeProductIds)
            }

            else -> baseModel
        }
    }

    private fun updateLikeStatus(product: Product, likeProductIds: List<String>): Product {
        return product.copy(isLike = likeProductIds.contains(product.productId))
    }
}