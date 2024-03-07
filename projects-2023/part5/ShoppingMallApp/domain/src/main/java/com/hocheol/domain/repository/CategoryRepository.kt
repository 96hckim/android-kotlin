package com.hocheol.domain.repository

import com.hocheol.domain.model.Category
import com.hocheol.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {

    fun getCategories(): Flow<List<Category>>

    fun getProductsByCategory(category: Category): Flow<List<Product>>

    suspend fun likeProduct(product: Product)
}