package com.hocheol.domain.usecase

import com.hocheol.domain.model.PurchaseHistory
import com.hocheol.domain.repository.PurchaseHistoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PurchaseHistoryUseCase @Inject constructor(
    private val purchaseHistoryRepository: PurchaseHistoryRepository
) {

    fun getPurchaseHistory(): Flow<List<PurchaseHistory>> {
        return purchaseHistoryRepository.getPurchaseHistory()
    }
}