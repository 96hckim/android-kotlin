package com.hocheol.presentation.viewmodel.category

import androidx.lifecycle.ViewModel
import com.hocheol.domain.model.Category
import com.hocheol.domain.model.Product
import com.hocheol.domain.usecase.CategoryUseCase
import com.hocheol.presentation.delegate.ProductDelegate
import com.hocheol.presentation.model.ProductVM
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val categoryUseCase: CategoryUseCase
) : ViewModel(), ProductDelegate {

    private val _products = MutableStateFlow<List<ProductVM>>(listOf())
    val products: StateFlow<List<ProductVM>> = _products

    suspend fun updateCategory(category: Category) {
        categoryUseCase.getProductsByCategory(category).collectLatest {
            _products.emit(convertToPresentationVM(it))
        }
    }

    override fun openProduct(product: Product) {

    }

    private fun convertToPresentationVM(productList: List<Product>): List<ProductVM> {
        return productList.map { product ->
            ProductVM(model = product, productDelegate = this)
        }
    }
}