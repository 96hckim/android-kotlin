package com.hocheol.todo.repository

import com.hocheol.todo.data.dao.ContentDao
import com.hocheol.todo.model.ContentEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ContentRepositoryImpl @Inject constructor(
    private val contentDao: ContentDao
) : ContentRepository {

    override fun loadList(): Flow<List<ContentEntity>> = contentDao.selectAll()

    override suspend fun insert(item: ContentEntity) {
        contentDao.insert(item)
    }
}