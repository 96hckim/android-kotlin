package com.hocheol.blind.domain.repository

import com.hocheol.blind.domain.model.Content

interface ContentRepository {

    suspend fun save(item: Content): Boolean

    suspend fun update(item: Content): Boolean
}