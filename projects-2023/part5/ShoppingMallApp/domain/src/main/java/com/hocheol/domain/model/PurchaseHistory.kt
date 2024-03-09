package com.hocheol.domain.model

import java.time.ZonedDateTime

data class PurchaseHistory(
    val basketList: List<BasketProduct>,
    val purchaseAt: ZonedDateTime
)
