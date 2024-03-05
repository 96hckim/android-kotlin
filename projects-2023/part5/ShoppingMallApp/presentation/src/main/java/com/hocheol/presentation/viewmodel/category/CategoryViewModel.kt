package com.hocheol.presentation.viewmodel.category

import androidx.lifecycle.ViewModel
import com.hocheol.domain.model.Category
import com.hocheol.domain.model.Product
import com.hocheol.domain.usecase.CategoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val categoryUseCase: CategoryUseCase
) : ViewModel() {

    private val _products = MutableStateFlow<List<Product>>(listOf())
    val products: StateFlow<List<Product>> = _products

    suspend fun updateCategory(category: Category) {
        categoryUseCase.getProductsByCategory(category).collectLatest {
            _products.emit(it)
        }
    }

    fun openProduct(product: Product) {

    }
}