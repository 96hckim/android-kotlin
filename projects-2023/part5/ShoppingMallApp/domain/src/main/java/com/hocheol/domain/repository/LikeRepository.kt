package com.hocheol.domain.repository

import com.hocheol.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface LikeRepository {

    fun getLikeProducts(): Flow<List<Product>>
}