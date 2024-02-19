package com.hocheol.blind.domain.repository

import com.hocheol.blind.domain.model.Content
import kotlinx.coroutines.flow.Flow

interface ContentRepository {

    fun loadList(): Flow<List<Content>>

    suspend fun save(item: Content): Boolean

    suspend fun update(item: Content): Boolean

    suspend fun delete(item: Content): Boolean
}