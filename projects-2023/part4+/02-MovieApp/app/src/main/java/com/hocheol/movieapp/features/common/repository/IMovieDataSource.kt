package com.hocheol.movieapp.features.common.repository

import com.hocheol.movieapp.features.common.entity.CategoryEntity
import com.hocheol.movieapp.features.common.entity.EntityWrapper
import com.hocheol.movieapp.features.common.entity.MovieDetailEntity
import com.hocheol.movieapp.features.feed.domain.SortOrder

interface IMovieDataSource {
    suspend fun getCategories(sortOrder: SortOrder? = null): EntityWrapper<List<CategoryEntity>>
    suspend fun getMovieDetail(movieName: String): MovieDetailEntity
}