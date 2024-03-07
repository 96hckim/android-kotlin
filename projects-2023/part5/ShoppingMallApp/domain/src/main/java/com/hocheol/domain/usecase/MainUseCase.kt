package com.hocheol.domain.usecase

import com.hocheol.domain.model.BaseModel
import com.hocheol.domain.model.Product
import com.hocheol.domain.repository.MainRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MainUseCase @Inject constructor(
    private val mainRepository: MainRepository
) {

    fun getModelList(): Flow<List<BaseModel>> {
        return mainRepository.getModelList()
    }

    suspend fun likeProduct(product: Product) {
        return mainRepository.likeProduct(product)
    }
}