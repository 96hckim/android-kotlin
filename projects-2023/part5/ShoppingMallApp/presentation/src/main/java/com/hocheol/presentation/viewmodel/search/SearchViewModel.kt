package com.hocheol.presentation.viewmodel.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.hocheol.domain.model.Product
import com.hocheol.domain.model.SearchKeyword
import com.hocheol.domain.usecase.SearchUseCase
import com.hocheol.presentation.delegate.ProductDelegate
import com.hocheol.presentation.model.ProductVM
import com.hocheol.presentation.ui.NavigationRouteName
import com.hocheol.presentation.utils.NavigationUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchUseCase: SearchUseCase
) : ViewModel(), ProductDelegate {

    private val _searchResult = MutableStateFlow<List<ProductVM>>(emptyList())
    val searchResult: StateFlow<List<ProductVM>> = _searchResult

    val searchKeywords = searchUseCase.getSearchKeywords()

    fun search(keyword: String) {
        viewModelScope.launch {
            searchUseCase.search(SearchKeyword(keyword)).collectLatest {
                _searchResult.emit(it.map(::convertToProductVM))
            }
        }
    }

    private fun convertToProductVM(product: Product): ProductVM {
        return ProductVM(model = product, productDelegate = this)
    }

    override fun openProduct(navController: NavHostController, product: Product) {
        NavigationUtils.navigate(navController, NavigationRouteName.PRODUCT_DETAIL, product)
    }
}