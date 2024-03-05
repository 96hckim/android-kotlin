package com.hocheol.presentation.ui.main

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.hocheol.presentation.ui.common.ProductCard
import com.hocheol.presentation.viewmodel.MainViewModel

@Composable
fun HomeScreen(
    viewModel: MainViewModel
) {
    val productList by viewModel.productList.collectAsState(initial = emptyList())
    val columnCount by viewModel.columnCount.collectAsState()

    LazyVerticalGrid(columns = GridCells.Fixed(count = columnCount)) {
        items(
            count = productList.size
        ) { index ->
            ProductCard(
                product = productList[index]
            ) {
                // TODO 상세화면 개발 시 추가
            }
        }
    }
}