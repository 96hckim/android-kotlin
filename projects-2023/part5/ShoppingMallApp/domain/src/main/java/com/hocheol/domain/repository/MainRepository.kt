package com.hocheol.domain.repository

import com.hocheol.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface MainRepository {

    fun getProductList(): Flow<List<Product>>
}