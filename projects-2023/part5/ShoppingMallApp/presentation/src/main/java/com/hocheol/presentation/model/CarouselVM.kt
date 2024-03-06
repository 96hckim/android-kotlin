package com.hocheol.presentation.model

import com.hocheol.domain.model.Carousel
import com.hocheol.domain.model.Product
import com.hocheol.presentation.delegate.ProductDelegate

class CarouselVM(
    model: Carousel,
    private val productDelegate: ProductDelegate
) : PresentationVM<Carousel>(model) {

    fun openCarouselProduct(product: Product) {
        productDelegate.openProduct(product)
        sendCarouselLog()
    }

    fun sendCarouselLog() {

    }
}