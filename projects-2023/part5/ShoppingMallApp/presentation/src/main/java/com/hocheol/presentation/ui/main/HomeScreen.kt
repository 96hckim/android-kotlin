package com.hocheol.presentation.ui.main

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.hocheol.domain.model.Banner
import com.hocheol.domain.model.BannerList
import com.hocheol.domain.model.Carousel
import com.hocheol.domain.model.ModelType
import com.hocheol.domain.model.Product
import com.hocheol.domain.model.Ranking
import com.hocheol.presentation.ui.component.BannerCard
import com.hocheol.presentation.ui.component.BannerListCard
import com.hocheol.presentation.ui.component.CarouselCard
import com.hocheol.presentation.ui.component.ProductCard
import com.hocheol.presentation.ui.component.RankingCard
import com.hocheol.presentation.viewmodel.MainViewModel

@Composable
fun HomeScreen(
    viewModel: MainViewModel
) {
    val modelList by viewModel.modelList.collectAsState(initial = emptyList())
    val columnCount by viewModel.columnCount.collectAsState()

    LazyVerticalGrid(columns = GridCells.Fixed(count = columnCount)) {
        items(
            count = modelList.size,
            span = { index ->
                val item = modelList[index]
                val spanCount = getSpanCountByType(item.type, columnCount)
                GridItemSpan(spanCount)
            }
        ) { index ->
            when (val item = modelList[index]) {
                is Product -> ProductCard(model = item) { product ->
                    viewModel.openProduct(product)
                }

                is Banner -> BannerCard(model = item) { banner ->
                    viewModel.openBanner(banner)
                }

                is BannerList -> BannerListCard(model = item) { bannerList ->
                    viewModel.openBannerList(bannerList)
                }

                is Carousel -> CarouselCard(model = item) { product ->
                    viewModel.openCarouselProduct(product)
                }

                is Ranking -> RankingCard(model = item) { product ->
                    viewModel.openRankingProduct(product)
                }
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