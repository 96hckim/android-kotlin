package com.hocheol.domain.repository

import com.hocheol.domain.model.Product

interface MainRepository {

    fun getProductList(): List<Product>
}