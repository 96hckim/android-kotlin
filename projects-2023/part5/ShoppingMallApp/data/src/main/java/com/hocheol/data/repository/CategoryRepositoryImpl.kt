package com.hocheol.data.repository

import com.hocheol.data.datasource.ProductDataSource
import com.hocheol.domain.model.Category
import com.hocheol.domain.model.Product
import com.hocheol.domain.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val productDataSource: ProductDataSource
) : CategoryRepository {

    override fun getCategories(): Flow<List<Category>> = flow {
        emit(
            listOf(
                Category.Top, Category.Bag, Category.Outerwear,
                Category.Dress, Category.FashionAccessories, Category.Pants,
                Category.Skirt, Category.Shoes
            )
        )
    }

    override fun getProductsByCategory(category: Category): Flow<List<Product>> {
        return productDataSource.getProducts().map { modelList ->
            modelList.filterIsInstance(Product::class.java)
                .filter { it.category.categoryId == category.categoryId }
        }
    }
}