package com.hocheol.domain.usecase

import com.hocheol.domain.model.Category
import com.hocheol.domain.model.Product
import com.hocheol.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CategoryUseCase @Inject constructor(
    private val repository: CategoryRepository
) {

    fun getCategories(): Flow<List<Category>> {
        return repository.getCategories()
    }

    fun getProductsByCategory(category: Category): Flow<List<Product>> {
        return repository.getProductsByCategory(category)
    }
}