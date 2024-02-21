package com.hocheol.movieapp.features.common.network.api

import com.hocheol.movieapp.features.common.network.model.MovieResponse
import com.hocheol.movieapp.library.network.model.ApiResult

interface IMovieAppNetworkApi {
    suspend fun getMovies(): ApiResult<List<MovieResponse>>
}