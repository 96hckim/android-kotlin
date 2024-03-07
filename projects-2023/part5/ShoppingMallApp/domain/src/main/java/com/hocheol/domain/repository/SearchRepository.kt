package com.hocheol.domain.repository

import com.hocheol.domain.model.Product
import com.hocheol.domain.model.SearchFilter
import com.hocheol.domain.model.SearchKeyword
import kotlinx.coroutines.flow.Flow

interface SearchRepository {

    suspend fun search(searchKeyword: SearchKeyword, filters: List<SearchFilter>): Flow<List<Product>>

    fun getSearchKeywords(): Flow<List<SearchKeyword>>

    suspend fun likeProduct(product: Product)
}