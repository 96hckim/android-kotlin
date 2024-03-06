package com.hocheol.domain.usecase

import com.hocheol.domain.model.Product
import com.hocheol.domain.model.SearchKeyword
import com.hocheol.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchUseCase @Inject constructor(
    private val searchRepository: SearchRepository
) {

    suspend fun search(searchKeyword: SearchKeyword): Flow<List<Product>> {
        return searchRepository.search(searchKeyword)
    }

    fun getSearchKeywords(): Flow<List<SearchKeyword>> {
        return searchRepository.getSearchKeywords()
    }
}