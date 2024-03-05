package com.hocheol.domain.usecase

import com.hocheol.domain.model.Product
import com.hocheol.domain.repository.MainRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MainUseCase @Inject constructor(
    private val mainRepository: MainRepository
) {

    fun getProductList(): Flow<List<Product>> {
        return mainRepository.getProductList()
    }
}