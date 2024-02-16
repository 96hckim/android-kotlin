package com.hocheol.todo.repository

import com.hocheol.todo.model.ContentEntity

interface ContentRepository {

    suspend fun insert(item: ContentEntity)
}