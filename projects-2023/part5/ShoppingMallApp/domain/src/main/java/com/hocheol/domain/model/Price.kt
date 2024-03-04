package com.hocheol.domain.model

data class Price(
    val originPrice: Int,
    val finalPrice: Int,
    val salesStatus: SalesStatus
)
