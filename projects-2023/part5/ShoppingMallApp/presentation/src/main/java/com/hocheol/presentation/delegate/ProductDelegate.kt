package com.hocheol.presentation.delegate

import com.hocheol.domain.model.Product

interface ProductDelegate {

    fun openProduct(product: Product)
}