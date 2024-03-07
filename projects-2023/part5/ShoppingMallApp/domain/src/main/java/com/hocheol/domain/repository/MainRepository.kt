package com.hocheol.domain.repository

import com.hocheol.domain.model.BaseModel
import com.hocheol.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface MainRepository {

    fun getModelList(): Flow<List<BaseModel>>

    suspend fun likeProduct(product: Product)
}