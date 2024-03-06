package com.hocheol.presentation.model

import com.hocheol.domain.model.Product
import com.hocheol.domain.model.Ranking
import com.hocheol.presentation.delegate.ProductDelegate

class RankingVM(
    model: Ranking,
    private val productDelegate: ProductDelegate
) : PresentationVM<Ranking>(model) {

    fun openRankingProduct(product: Product) {
        productDelegate.openProduct(product)
        sendRankingLog()
    }

    private fun sendRankingLog() {

    }
}