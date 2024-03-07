package com.hocheol.presentation.delegate

import androidx.navigation.NavHostController
import com.hocheol.domain.model.Product

interface ProductDelegate {

    fun openProduct(navController: NavHostController, product: Product)

    fun likeProduct(product: Product)
}