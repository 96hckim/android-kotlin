package com.hocheol.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.hocheol.domain.model.Banner
import com.hocheol.domain.model.BannerList
import com.hocheol.domain.model.BaseModel
import com.hocheol.domain.model.Carousel
import com.hocheol.domain.model.Category
import com.hocheol.domain.model.Product
import com.hocheol.domain.model.Ranking
import com.hocheol.domain.usecase.CategoryUseCase
import com.hocheol.domain.usecase.MainUseCase
import com.hocheol.presentation.delegate.BannerDelegate
import com.hocheol.presentation.delegate.CategoryDelegate
import com.hocheol.presentation.delegate.ProductDelegate
import com.hocheol.presentation.model.BannerListVM
import com.hocheol.presentation.model.BannerVM
import com.hocheol.presentation.model.CarouselVM
import com.hocheol.presentation.model.PresentationVM
import com.hocheol.presentation.model.ProductVM
import com.hocheol.presentation.model.RankingVM
import com.hocheol.presentation.ui.NavigationRouteName
import com.hocheol.presentation.utils.NavigationUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    mainUseCase: MainUseCase,
    categoryUseCase: CategoryUseCase
) : ViewModel(), ProductDelegate, BannerDelegate, CategoryDelegate {

    private val _columnCount = MutableStateFlow(DEFAULT_COLUMN_COUNT)
    val columnCount: StateFlow<Int> = _columnCount

    val modelList = mainUseCase.getModelList().map(::convertToPresentationVM)
    val categories = categoryUseCase.getCategories()

    fun openSearchForm() {
        println("openSearchForm")
    }

    fun updateColumnCount(count: Int) {
        viewModelScope.launch {
            _columnCount.emit(count)
        }
    }

    override fun openProduct(navController: NavHostController, product: Product) {
        NavigationUtils.navigate(navController, NavigationRouteName.PRODUCT_DETAIL, product)
    }

    override fun openBanner(bannerId: String) {

    }

    override fun openCategory(navController: NavHostController, category: Category) {
        NavigationUtils.navigate(navController, NavigationRouteName.CATEGORY_DETAIL, category)
    }

    private fun convertToPresentationVM(modelList: List<BaseModel>): List<PresentationVM<out BaseModel>> {
        return modelList.map { model ->
            when (model) {
                is Product -> ProductVM(model, this)
                is Banner -> BannerVM(model, this)
                is BannerList -> BannerListVM(model, this)
                is Carousel -> CarouselVM(model, this)
                is Ranking -> RankingVM(model, this)
            }
        }
    }

    companion object {
        private const val DEFAULT_COLUMN_COUNT = 2
    }
}