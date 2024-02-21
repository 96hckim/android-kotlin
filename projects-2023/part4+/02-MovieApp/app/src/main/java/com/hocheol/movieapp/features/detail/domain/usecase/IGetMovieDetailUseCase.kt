package com.hocheol.movieapp.features.detail.domain.usecase

import com.hocheol.movieapp.features.common.entity.MovieDetailEntity

interface IGetMovieDetailUseCase {
    suspend operator fun invoke(name: String): MovieDetailEntity
}