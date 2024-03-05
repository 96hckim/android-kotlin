package com.hocheol.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.hocheol.domain.model.Banner
import com.hocheol.domain.model.BannerList
import com.hocheol.domain.model.Category
import com.hocheol.domain.model.Product
import com.hocheol.domain.usecase.CategoryUseCase
import com.hocheol.domain.usecase.MainUseCase
import com.hocheol.presentation.ui.NavigationRouteName
import com.hocheol.presentation.utils.NavigationUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    mainUseCase: MainUseCase,
    categoryUseCase: CategoryUseCase
) : ViewModel() {

    private val _columnCount = MutableStateFlow(DEFAULT_COLUMN_COUNT)
    val columnCount: StateFlow<Int> = _columnCount

    val modelList = mainUseCase.getModelList()
    val categories = categoryUseCase.getCategories()

    fun openSearchForm() {
        println("openSearchForm")
    }

    fun updateColumnCount(count: Int) {
        viewModelScope.launch {
            _columnCount.emit(count)
        }
    }

    fun openProduct(product: Product) {

    }

    fun openBanner(banner: Banner) {

    }

    fun openBannerList(bannerList: BannerList) {

    }

    fun openCarouselProduct(product: Product) {

    }

    fun openRankingProduct(product: Product) {

    }

    fun openCategory(navController: NavHostController, category: Category) {
        NavigationUtils.navigate(navController, NavigationRouteName.CATEGORY_DETAIL, category)
    }

    companion object {
        private const val DEFAULT_COLUMN_COUNT = 2
    }
}