package com.hocheol.movieapp.features.feed.domain.usecase

import com.hocheol.movieapp.features.common.entity.CategoryEntity
import com.hocheol.movieapp.features.common.entity.EntityWrapper
import com.hocheol.movieapp.features.common.repository.IMovieDataSource
import com.hocheol.movieapp.features.feed.domain.SortOrder
import javax.inject.Inject

class GetFeedCategoryUseCase @Inject constructor(
    private val dataSource: IMovieDataSource
) : IGetFeedCategoryUseCase {
    override suspend fun invoke(sortOrder: SortOrder?): EntityWrapper<List<CategoryEntity>> {
        return dataSource.getCategories(sortOrder)
    }
}