package com.hocheol.movieapp.features.detail.presentation.output

import com.hocheol.movieapp.features.common.entity.MovieDetailEntity

sealed class MovieDetailState {
    data object Initial : MovieDetailState()
    class Main(val movieDetailEntity: MovieDetailEntity) : MovieDetailState()
}