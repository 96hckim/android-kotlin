package com.hocheol.presentation.ui.main

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import com.hocheol.domain.model.ModelType
import com.hocheol.presentation.model.BannerListVM
import com.hocheol.presentation.model.BannerVM
import com.hocheol.presentation.model.CarouselVM
import com.hocheol.presentation.model.ProductVM
import com.hocheol.presentation.model.RankingVM
import com.hocheol.presentation.ui.component.BannerCard
import com.hocheol.presentation.ui.component.BannerListCard
import com.hocheol.presentation.ui.component.CarouselCard
import com.hocheol.presentation.ui.component.ProductCard
import com.hocheol.presentation.ui.component.RankingCard
import com.hocheol.presentation.viewmodel.MainViewModel

@Composable
fun HomeScreen(
    navHostController: NavHostController,
    viewModel: MainViewModel
) {
    val modelList by viewModel.modelList.collectAsState(initial = emptyList())
    val columnCount by viewModel.columnCount.collectAsState()

    LazyVerticalGrid(columns = GridCells.Fixed(count = columnCount)) {
        items(
            count = modelList.size,
            span = { index ->
                val item = modelList[index]
                val spanCount = getSpanCountByType(item.model.type, columnCount)
                GridItemSpan(spanCount)
            }
        ) { index ->
            when (val item = modelList[index]) {
                is ProductVM -> ProductCard(navHostController = navHostController, presentationVM = item)
                is BannerVM -> BannerCard(presentationVM = item)
                is BannerListVM -> BannerListCard(presentationVM = item)
                is CarouselVM -> CarouselCard(navHostController = navHostController, presentationVM = item)
                is RankingVM -> RankingCard(navHostController = navHostController, presentationVM = item)
            }
        }
    }
}

private fun getSpanCountByType(type: ModelType, defaultColumnCount: Int): Int {
    return when (type) {
        ModelType.PRODUCT -> 1
        ModelType.BANNER, ModelType.BANNER_LIST, ModelType.CAROUSEL, ModelType.RANKING -> defaultColumnCount
    }
}