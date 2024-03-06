package com.hocheol.presentation.model

import androidx.navigation.NavHostController
import com.hocheol.domain.model.Product
import com.hocheol.domain.model.Ranking
import com.hocheol.presentation.delegate.ProductDelegate

class RankingVM(
    model: Ranking,
    private val productDelegate: ProductDelegate
) : PresentationVM<Ranking>(model) {

    fun openRankingProduct(navController: NavHostController, product: Product) {
        productDelegate.openProduct(navController, product)
        sendRankingLog()
    }

    private fun sendRankingLog() {

    }
}