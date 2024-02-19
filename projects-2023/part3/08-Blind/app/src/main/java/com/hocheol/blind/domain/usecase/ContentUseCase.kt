package com.hocheol.blind.domain.usecase

import com.hocheol.blind.domain.model.Content
import com.hocheol.blind.domain.repository.ContentRepository
import javax.inject.Inject

class ContentUseCase @Inject constructor(
    private val contentRepository: ContentRepository
) {

    suspend fun save(item: Content) = contentRepository.save(item)
}