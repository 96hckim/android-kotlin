package com.hocheol.movieapp.features.common.repository

import com.hocheol.movieapp.features.common.entity.CategoryEntity
import com.hocheol.movieapp.features.common.entity.EntityWrapper
import com.hocheol.movieapp.features.common.entity.MovieDetailEntity
import com.hocheol.movieapp.features.common.network.api.IMovieAppNetworkApi
import com.hocheol.movieapp.features.feed.data.FeedConstants
import com.hocheol.movieapp.features.feed.data.mapper.CategoryMapper
import com.hocheol.movieapp.features.feed.domain.SortOrder
import com.hocheol.movieapp.library.storage.IStorage
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val movieNetworkApi: IMovieAppNetworkApi,
    private val storage: IStorage,
    private val categoryMapper: CategoryMapper
) : IMovieDataSource {
    override suspend fun getCategories(sortOrder: SortOrder?): EntityWrapper<List<CategoryEntity>> {
        return categoryMapper.mapFromResult(
            result = movieNetworkApi.getMovies(),
            extra = sortOrder
        )
    }

    override suspend fun getMovieDetail(movieName: String): MovieDetailEntity {
        return storage
            .get<List<MovieDetailEntity>>(FeedConstants.MOVIE_LIST_KEY)
            ?.single { it.title == movieName }
            ?: MovieDetailEntity()
    }
}