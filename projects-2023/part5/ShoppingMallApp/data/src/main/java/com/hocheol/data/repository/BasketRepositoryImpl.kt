package com.hocheol.data.repository

import com.hocheol.data.db.dao.BasketDao
import com.hocheol.data.db.dao.PurchaseHistoryDao
import com.hocheol.data.db.entity.PurchaseHistoryEntity
import com.hocheol.data.db.entity.toDomainModel
import com.hocheol.domain.model.BasketProduct
import com.hocheol.domain.model.Product
import com.hocheol.domain.repository.BasketRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.ZonedDateTime
import javax.inject.Inject

class BasketRepositoryImpl @Inject constructor(
    private val basketDao: BasketDao,
    private val purchaseHistoryDao: PurchaseHistoryDao
) : BasketRepository {

    override fun getBasketProducts(): Flow<List<BasketProduct>> {
        return basketDao.getAll().map { entities ->
            entities.map { entity -> BasketProduct(entity.toDomainModel(), entity.count) }
        }
    }

    override suspend fun removeBasketProduct(product: Product) {
        basketDao.delete(product.productId)
    }

    override suspend fun checkoutBasket(products: List<BasketProduct>) {
        purchaseHistoryDao.insert(
            PurchaseHistoryEntity(
                basketList = products,
                purchaseAt = ZonedDateTime.now()
            )
        )
        basketDao.deleteAll()
    }
}