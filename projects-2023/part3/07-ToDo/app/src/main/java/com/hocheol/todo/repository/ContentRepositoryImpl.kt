package com.hocheol.todo.repository

import com.hocheol.todo.data.dao.ContentDao
import com.hocheol.todo.model.ContentEntity
import javax.inject.Inject

class ContentRepositoryImpl @Inject constructor(
    private val contentDao: ContentDao
) : ContentRepository {

    override suspend fun insert(item: ContentEntity) {
        contentDao.insert(item)
    }
}