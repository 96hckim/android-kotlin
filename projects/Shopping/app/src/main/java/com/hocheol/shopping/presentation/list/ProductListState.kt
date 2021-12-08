package com.hocheol.shopping.presentation.list

import com.hocheol.shopping.data.entity.product.ProductEntity

sealed class ProductListState {

    object UnInitialized : ProductListState()

    object Loading : ProductListState()

    data class Success(
        val productList: List<ProductEntity>
    ) : ProductListState()

    object Error : ProductListState()

}
