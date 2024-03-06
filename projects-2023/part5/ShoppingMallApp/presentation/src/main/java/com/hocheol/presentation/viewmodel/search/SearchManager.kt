package com.hocheol.presentation.viewmodel.search

import com.hocheol.domain.model.Product
import com.hocheol.domain.model.SearchFilter
import com.hocheol.domain.model.SearchKeyword
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SearchManager {
    private val _filters = MutableStateFlow<List<SearchFilter>>(emptyList())
    val filters: StateFlow<List<SearchFilter>> = _filters

    var searchKeyword = SearchKeyword("")
        private set

    suspend fun initSearchManager(newSearchKeyword: String, searchResult: List<Product>) {
        val categories = searchResult.map { it.category }.distinctBy { it.categoryId }
        val (minPrice, maxPrice) = calculatePriceRange(searchResult)

        val newFilters = listOf(
            SearchFilter.PriceFilter(minPrice.toFloat() to maxPrice.toFloat()),
            SearchFilter.CategoryFilter(categories)
        )

        _filters.emit(newFilters)
        searchKeyword = SearchKeyword(newSearchKeyword)
    }

    private fun calculatePriceRange(searchResult: List<Product>): Pair<Int, Int> {
        var minPrice = Int.MAX_VALUE
        var maxPrice = Int.MIN_VALUE

        searchResult.forEach {
            minPrice = minOf(minPrice, it.price.finalPrice)
            maxPrice = maxOf(maxPrice, it.price.finalPrice)
        }

        return minPrice to maxPrice
    }

    suspend fun updateFilter(filter: SearchFilter) {
        val updatedFilters = filters.value.map {
            when (it) {
                is SearchFilter.PriceFilter -> {
                    if (filter is SearchFilter.PriceFilter) {
                        it.copy(selectedRange = filter.selectedRange)
                    } else it
                }

                is SearchFilter.CategoryFilter -> {
                    if (filter is SearchFilter.CategoryFilter) {
                        it.copy(selectedCategory = filter.selectedCategory)
                    } else it
                }
            }
        }
        _filters.emit(updatedFilters)
    }

    fun clearFilter() {
        _filters.value.forEach { it.clear() }
    }

    fun currentFilters(): List<SearchFilter> = filters.value
}