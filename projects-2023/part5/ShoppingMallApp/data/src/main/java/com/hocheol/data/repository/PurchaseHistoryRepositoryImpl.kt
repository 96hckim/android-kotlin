package com.hocheol.data.repository

import com.hocheol.data.db.dao.PurchaseHistoryDao
import com.hocheol.data.db.entity.toDomainModel
import com.hocheol.domain.model.PurchaseHistory
import com.hocheol.domain.repository.PurchaseHistoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PurchaseHistoryRepositoryImpl @Inject constructor(
    private val purchaseHistoryDao: PurchaseHistoryDao
) : PurchaseHistoryRepository {

    override fun getPurchaseHistory(): Flow<List<PurchaseHistory>> {
        return purchaseHistoryDao.getAll().map { entities ->
            entities.map { entity -> entity.toDomainModel() }
        }
    }
}