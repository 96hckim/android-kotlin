package com.hocheol.domain.usecase

import com.hocheol.domain.model.Category
import com.hocheol.domain.model.Product
import com.hocheol.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CategoryUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
) {

    fun getCategories(): Flow<List<Category>> {
        return categoryRepository.getCategories()
    }

    fun getProductsByCategory(category: Category): Flow<List<Product>> {
        return categoryRepository.getProductsByCategory(category)
    }

    suspend fun likeProduct(product: Product) {
        return categoryRepository.likeProduct(product)
    }
}