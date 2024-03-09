package com.hocheol.domain.repository

import com.hocheol.domain.model.PurchaseHistory
import kotlinx.coroutines.flow.Flow

interface PurchaseHistoryRepository {

    fun getPurchaseHistory(): Flow<List<PurchaseHistory>>
}