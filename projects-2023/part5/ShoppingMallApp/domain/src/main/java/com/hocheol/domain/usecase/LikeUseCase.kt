package com.hocheol.domain.usecase

import com.hocheol.domain.model.Product
import com.hocheol.domain.repository.LikeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LikeUseCase @Inject constructor(
    private val likeRepository: LikeRepository
) {

    fun getLikeProducts(): Flow<List<Product>> {
        return likeRepository.getLikeProducts()
    }
}