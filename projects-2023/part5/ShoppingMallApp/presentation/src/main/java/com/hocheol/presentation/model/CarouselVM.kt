package com.hocheol.presentation.model

import androidx.navigation.NavHostController
import com.hocheol.domain.model.Carousel
import com.hocheol.domain.model.Product
import com.hocheol.presentation.delegate.ProductDelegate

class CarouselVM(
    model: Carousel,
    private val productDelegate: ProductDelegate
) : PresentationVM<Carousel>(model) {

    fun openCarouselProduct(navController: NavHostController, product: Product) {
        productDelegate.openProduct(navController, product)
        sendCarouselLog()
    }

    fun likeProduct(product: Product) {
        productDelegate.likeProduct(product)
    }

    fun sendCarouselLog() {

    }
}