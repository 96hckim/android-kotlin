package com.hocheol.presentation.ui.main

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.hocheol.domain.model.Banner
import com.hocheol.domain.model.BannerList
import com.hocheol.domain.model.ModelType
import com.hocheol.domain.model.Product
import com.hocheol.presentation.ui.component.BannerCard
import com.hocheol.presentation.ui.component.BannerListCard
import com.hocheol.presentation.ui.component.ProductCard
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
                is Product -> ProductCard(product = item) {
                    // TODO 상세화면 개발 시 추가
                }

                is Banner -> BannerCard(model = item)

                is BannerList -> BannerListCard(model = item)
            }
        }
    }
}

private fun getSpanCountByType(type: ModelType, defaultColumnCount: Int): Int {
    return when (type) {
        ModelType.PRODUCT -> 1
        ModelType.BANNER, ModelType.BANNER_LIST -> defaultColumnCount
    }
}