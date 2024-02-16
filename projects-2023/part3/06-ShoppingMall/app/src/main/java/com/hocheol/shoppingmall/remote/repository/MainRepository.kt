package com.hocheol.shoppingmall.remote.repository

import androidx.paging.PagingData
import com.hocheol.shoppingmall.model.ListItem
import kotlinx.coroutines.flow.Flow

interface MainRepository {

    fun loadList(): Flow<PagingData<ListItem>>
}