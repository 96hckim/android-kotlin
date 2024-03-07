package com.hocheol.domain.usecase

import com.hocheol.domain.model.Product
import com.hocheol.domain.model.SearchFilter
import com.hocheol.domain.model.SearchKeyword
import com.hocheol.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchUseCase @Inject constructor(
    private val searchRepository: SearchRepository
) {

    suspend fun search(searchKeyword: SearchKeyword, filters: List<SearchFilter>): Flow<List<Product>> {
        return searchRepository.search(searchKeyword, filters)
    }

    fun getSearchKeywords(): Flow<List<SearchKeyword>> {
        return searchRepository.getSearchKeywords()
    }

    suspend fun likeProduct(product: Product) {
        return searchRepository.likeProduct(product)
    }
}