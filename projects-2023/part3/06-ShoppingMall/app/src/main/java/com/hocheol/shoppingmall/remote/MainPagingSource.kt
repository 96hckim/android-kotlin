package com.hocheol.shoppingmall.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.hocheol.shoppingmall.model.ListItem

class MainPagingSource(
    private val mainService: MainService
) : PagingSource<Int, ListItem>() {
    override fun getRefreshKey(state: PagingState<Int, ListItem>) = 0

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ListItem> {
        return try {
            val page = params.key ?: 1
            val size = params.loadSize
            val result = mainService.getList(page, size).data

            LoadResult.Page(
                data = result.list,
                prevKey = null,
                nextKey = result.page.nextPage
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}