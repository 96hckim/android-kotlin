package com.hocheol.domain.model

sealed class Category(
    val categoryId: String,
    val categoryName: String
) {
    data object Top : Category("1", "상의")
    data object Outerwear : Category("2", "아우터")
    data object Dress : Category("3", "원피스")
    data object Pants : Category("4", "바지")
    data object Skirt : Category("5", "치마")
    data object Shoes : Category("6", "신발")
    data object Bag : Category("7", "가방")
    data object FashionAccessories : Category("8", "패션잡화")
}
