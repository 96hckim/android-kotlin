package com.hocheol.blind.data.repository

import com.hocheol.blind.data.model.ContentMapper.toEntity
import com.hocheol.blind.data.model.ContentMapper.toRequest
import com.hocheol.blind.data.source.local.dao.ContentDao
import com.hocheol.blind.data.source.remote.api.ContentService
import com.hocheol.blind.domain.model.Content
import com.hocheol.blind.domain.repository.ContentRepository
import java.io.IOException
import javax.inject.Inject

class ContentRepositoryImpl @Inject constructor(
    private val contentService: ContentService,
    private val contentDao: ContentDao
) : ContentRepository {

    override suspend fun save(item: Content): Boolean {
        return try {
            contentService.saveItem(item.toRequest())
            contentDao.insert(item.toEntity())
            true
        } catch (e: IOException) {
            e.printStackTrace()
            false
        }
    }
}