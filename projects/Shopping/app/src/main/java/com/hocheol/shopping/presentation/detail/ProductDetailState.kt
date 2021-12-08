package com.hocheol.shopping.presentation.detail

import com.hocheol.shopping.data.entity.product.ProductEntity

sealed class ProductDetailState {

    object UnInitialized : ProductDetailState()

    object Loading : ProductDetailState()

    data class Success(
        val productEntity: ProductEntity
    ) : ProductDetailState()

    object Order : ProductDetailState()

    object Error : ProductDetailState()

}
