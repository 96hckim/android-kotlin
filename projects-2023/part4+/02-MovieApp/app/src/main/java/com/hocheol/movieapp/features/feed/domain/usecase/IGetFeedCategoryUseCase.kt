package com.hocheol.movieapp.features.feed.domain.usecase

import com.hocheol.movieapp.features.common.entity.CategoryEntity
import com.hocheol.movieapp.features.common.entity.EntityWrapper
import com.hocheol.movieapp.features.feed.domain.SortOrder

interface IGetFeedCategoryUseCase {
    suspend operator fun invoke(sortOrder: SortOrder? = null): EntityWrapper<List<CategoryEntity>>
}